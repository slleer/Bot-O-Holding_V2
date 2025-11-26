package com.botofholding.bot.SlashCommands.Parsers.User;


import com.botofholding.bot.Config.CommandConfig;
import com.botofholding.contract.DTO.Request.UserRequestDto;
import com.botofholding.bot.SlashCommands.Parsers.RequestBodyParser;
import com.botofholding.bot.Service.ApiClient;
import com.botofholding.bot.SlashCommands.Parsers.UserParser;
import com.botofholding.bot.Utility.EventUtility;
import com.botofholding.bot.Utility.MessageFormatter;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Optional;


@Component
public class UpdateUserParser implements UserParser, RequestBodyParser<UserRequestDto> {

    private static final Logger logger = LoggerFactory.getLogger(UpdateUserParser.class);
    private final CommandConfig commandConfig;

    @Autowired
    public UpdateUserParser(CommandConfig commandConfig) {
        this.commandConfig = commandConfig;
    }

    @Override
    public String getSubCommandName() {
        return commandConfig.getSubcmdUserUpdate();
    }

    @Override
    public String getContext() {
        return commandConfig.getContextUserUpdate();
    }

    @Override
    public Mono<Void> execute(ChatInputInteractionEvent event, ApiClient apiClient) {
        logger.info("Executing '{}' command for user {}", getContext(), EventUtility.getInvokingUserTag(event));

        Mono<Boolean> userEphemeralMono = getEphemeralSetting(apiClient);

        Mono<String> replyMono = buildRequestDto(event) // This now returns Mono<UserRequestDto>
                .flatMap(apiClient::updateMyProfile)
                .map(payload -> MessageFormatter.formatUserReply(payload.data(), payload.message()));

        return Mono.zip(userEphemeralMono, replyMono)
                .flatMap(tuple -> {
                    boolean isEphemeral = tuple.getT1();
                    String message = tuple.getT2();
                    return event.reply(message).withEphemeral(isEphemeral);
                })
                .contextWrite(ctx -> EventUtility.addUserContext(ctx, EventUtility.getInvokingUser(event)))
                .then();
    }

    @Override
    public Mono<UserRequestDto> buildRequestDto(ChatInputInteractionEvent event) {
        return Mono.justOrEmpty(EventUtility.getInvokingUser(event))
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Error retrieving User from event")))
                .map(targetUser -> {
                    String username = targetUser.getUsername();
                    String userTag = targetUser.getTag();
                    Optional<String> userGlobalName = targetUser.getGlobalName();
                    return new UserRequestDto(username, userTag, userGlobalName);
                });
    }

}
