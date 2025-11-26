package com.botofholding.bot.SlashCommands.Parsers.Inventory;

import com.botofholding.bot.Config.CommandConfig;
import com.botofholding.contract.DTO.Request.ModifyItemRequestDto;
import com.botofholding.bot.Domain.Entities.AutocompleteSelection;
import com.botofholding.bot.Domain.Entities.Reply;
import com.botofholding.bot.Service.ApiClient;
import com.botofholding.bot.SlashCommands.Parsers.InventoryParser;
import com.botofholding.bot.SlashCommands.Parsers.RequestBodyParser;
import com.botofholding.bot.Utility.EventUtility;
import com.botofholding.bot.Utility.MessageFormatter;
import com.botofholding.bot.Utility.ReplyUtility;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
public class ModifyInventoryParser implements InventoryParser, RequestBodyParser<ModifyItemRequestDto> {

    private static final Logger logger = LoggerFactory.getLogger(ModifyInventoryParser.class);
    private final CommandConfig commandConfig;

    @Autowired
    public ModifyInventoryParser(CommandConfig commandConfig) {
        this.commandConfig = commandConfig;
    }

    @Override
    public String getSubCommandName() {
        return commandConfig.getSubcmdInventoryModify();
    }

    @Override
    public String getContext() {
        return commandConfig.getContextInventoryModify();
    }

    @Override
    public Mono<Void> execute(ChatInputInteractionEvent event, ApiClient apiClient) {
        Mono<ModifyItemRequestDto> requestDtoMono = buildRequestDto(event);
        Mono<Boolean> ephemeralMono = getEphemeralSetting(apiClient);

        Mono<Reply> replyMono = Mono.zip(requestDtoMono, ephemeralMono)
                .flatMap(tuple -> {
                    ModifyItemRequestDto requestDto = tuple.getT1();
                    boolean useEphemeral = tuple.getT2();
                    return apiClient.modifyItemInActiveContainer(requestDto)
                            .map(payload -> new Reply(MessageFormatter.formatModifyInventoryContainerReply(payload.data(), payload.message()), useEphemeral));
                });
        logger.info("Replying after apiClient call in ModifyInventoryParser");
        return replyMono
                .flatMap(reply -> ReplyUtility.sendMultiPartReply(event, reply.message(), reply.isEphemeral()))
                .contextWrite(ctx -> EventUtility.addUserContext(ctx, EventUtility.getInvokingUser(event)))
                .then();
    }

    @Override
    public Mono<ModifyItemRequestDto> buildRequestDto(ChatInputInteractionEvent event) {
        Mono<AutocompleteSelection> selectionMono = EventUtility.getAutocompleteSelection(event, getSubCommandName(), commandConfig.getOptionItem());
        Mono<Optional<String>> noteMono = EventUtility.getOptionValueAsString(event, getSubCommandName(), commandConfig.getOptionNote()).map(Optional::of);
        Mono<Boolean> moveToRootMono = Mono.just(EventUtility.getOptionValue(event, getSubCommandName(), commandConfig.getOptionInventoryModifyMoveToRoot()).isPresent());
        Mono<AutocompleteSelection> moveInsideMono = EventUtility.getAutocompleteSelection(event, getSubCommandName(), commandConfig.getOptionInventoryModifyMoveInside())
                .defaultIfEmpty(new AutocompleteSelection("", null));

        return Mono.zip(selectionMono, noteMono, moveToRootMono, moveInsideMono)
                .flatMap(tuple -> {
                    AutocompleteSelection selection = tuple.getT1();
                    Optional<String> note = tuple.getT2();
                    boolean moveToRoot = tuple.getT3();
                    AutocompleteSelection moveInside = tuple.getT4();
                    ModifyItemRequestDto dto = new ModifyItemRequestDto();
                    dto.setContainerItemId(selection.id());
                    dto.setContainerItemName(selection.name());
                    note.ifPresent(dto::setNote);
                    dto.setMoveToRoot(moveToRoot);
                    dto.setNewParentId(moveInside.id());
                    dto.setNewParentName(moveInside.name());
                    logger.debug("selection: {}, note: {}, moveToRoot: {}, moveInside: {}", selection, note, moveToRoot, moveInside);
                    return Mono.just(dto);
                });
    }
}
