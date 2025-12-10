package com.botofholding.bot.AutoComplete.Providers.Inventory;

import com.botofholding.bot.AutoComplete.Providers.AbstractActiveParentItemNameProvider;
import com.botofholding.bot.AutoComplete.Providers.InventoryProvider;
import com.botofholding.bot.Config.CommandConfig;
import com.botofholding.bot.Service.ApiClient;
import com.botofholding.bot.Utility.EventUtility;
import discord4j.core.event.domain.interaction.ChatInputAutoCompleteEvent;
import discord4j.discordjson.json.ApplicationCommandOptionChoiceData;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class InsideModifyInventoryProvider extends AbstractActiveParentItemNameProvider implements InventoryProvider {

    @Autowired
    public InsideModifyInventoryProvider(ApiClient apiClient, CommandConfig commandConfig) {
        super(apiClient ,commandConfig);
    }

    @Override
    public String getSubCommandName() {
        return commandConfig.getSubcmdInventoryModify();
    }

    @Override
    public String getOptionName() {
        return commandConfig.getOptionInventoryModifyMoveInside();
    }

    @Override
    public Mono<Void> handle(ChatInputAutoCompleteEvent event) {
        LoggerFactory.getLogger(getClass())
                .debug("Fetching autocomplete suggestions for command '{}', option '{}' for user {}",
                        getSubCommandName(),
                        getOptionName(),
                        EventUtility.getInvokingUserTag(event));

        return buildRequestDto(event)
                .flatMap(this::fetchSuggestions) // Calls the concrete implementation
                .flatMap(response -> {
                    List<ApplicationCommandOptionChoiceData> suggestions = response.stream()
                            .limit(getCommandConfig().getDiscordChoiceLimit()-1)
                            .map(dto -> ApplicationCommandOptionChoiceData
                                    .builder()
                                    .name(buildOptionName(dto))
                                    .value(buildOptionValue(dto))
                                    .build()
                            ).collect(Collectors.toList());
                    suggestions.add(0, ApplicationCommandOptionChoiceData.builder().name(commandConfig.getOptionInventoryModifyMoveToRootDescription()).value("Root:-1").build());
                    return event.respondWithSuggestions(suggestions);
                })
                .contextWrite(ctx -> EventUtility.addUserContext(ctx, EventUtility.getInvokingUser(event)))
                .then();
    }
}
