package com.botofholding.bot.SlashCommands.Parsers.Inventory;

import com.botofholding.bot.Config.CommandConfig;
import com.botofholding.bot.Domain.Entities.AutocompleteSelection;
import com.botofholding.bot.Domain.Entities.Reply;
import com.botofholding.bot.Service.ApiClient;
import com.botofholding.bot.SlashCommands.Parsers.InventoryParser;
import com.botofholding.bot.Utility.EventUtility;
import com.botofholding.bot.Utility.MessageFormatter;
import com.botofholding.bot.Utility.ReplyUtility;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class DropInventoryParser implements InventoryParser {

    private static final Logger logger = LoggerFactory.getLogger(DropInventoryParser.class);
    private final CommandConfig commandConfig;

    public DropInventoryParser(CommandConfig commandConfig) {
        this.commandConfig = commandConfig;
    }

    @Override
    public String getSubCommandName() {
        return commandConfig.getSubcmdInventoryDrop();
    }

    @Override
    public String getContext() {
        return commandConfig.getContextInventoryDrop();
    }

    @Override
    public Mono<Void> execute(ChatInputInteractionEvent event, ApiClient apiClient) {
        logger.info("Attempting to drop item from active container");
        // 1. Get the item selection using our new centralized utility method.
        Mono<AutocompleteSelection> selectionMono = EventUtility.getAutocompleteSelection(event, getSubCommandName(), commandConfig.getOptionItem());

        // 2. Get the quantity to drop, defaulting to 1.
        Mono<Integer> quantityMono = EventUtility.getOptionValueAsLong(event, getSubCommandName(), commandConfig.getOptionQuantity())
                .map(Long::intValue)
                .defaultIfEmpty(1);

        Mono<Boolean> dropChildrenMono = Mono.just(EventUtility.getOptionValue(event, getSubCommandName(), commandConfig.getOptionInventoryDropDropChildren()).isPresent());

        // 4. Get the user's ephemeral setting for replies.
        Mono<Boolean> ephemeralMono = getEphemeralSetting(apiClient);


        Mono<Reply> replyMono = Mono.zip(selectionMono, quantityMono, dropChildrenMono, ephemeralMono)
                .flatMap(tuple -> {
                    AutocompleteSelection selection = tuple.getT1();
                    Integer quantity = tuple.getT2();
                    Boolean dropChildren = tuple.getT3();
                    boolean useEphemeral = tuple.getT4();
                    logger.info("Making call to apiClient.dropItemFromActiveContainer with values: id={}, name='{}', quantity={}.", selection.id(), selection.name(), quantity);
                    return apiClient.dropItemFromActiveContainer(selection.id(), selection.name(), dropChildren, quantity)
                            .map(payload -> new Reply(MessageFormatter.formatDropInventoryContainerReply(payload.data(), payload.message()), useEphemeral));
                });
        return replyMono.flatMap(reply -> ReplyUtility.sendMultiPartReply(event, reply.message(), reply.isEphemeral()))
                .contextWrite(ctx -> EventUtility.addUserContext(ctx, EventUtility.getInvokingUser(event)))
                .then();
    }
}
