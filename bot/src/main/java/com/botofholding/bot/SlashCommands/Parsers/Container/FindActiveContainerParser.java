package com.botofholding.bot.SlashCommands.Parsers.Container;

import com.botofholding.bot.Config.CommandConfig;
import com.botofholding.bot.Service.ApiClient;
import com.botofholding.bot.SlashCommands.Parsers.ContainerParser;
import com.botofholding.bot.Utility.EventUtility;
import com.botofholding.bot.Utility.MessageFormatter;
import com.botofholding.bot.Utility.ReplyUtility;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class FindActiveContainerParser implements ContainerParser {
    private static final Logger logger = LoggerFactory.getLogger(FindActiveContainerParser.class);
    private final CommandConfig commandConfig;
    private final MessageFormatter messageFormatter;

    @Autowired
    public FindActiveContainerParser(CommandConfig commandConfig, MessageFormatter messageFormatter) {
        this.commandConfig = commandConfig;
        this.messageFormatter = messageFormatter;
    }

    @Override
    public String getSubCommandName() {
        return commandConfig.getSubcmdContainerFindActive();
    }

    @Override
    public String getContext() {
        return commandConfig.getContextContainerFindActive();
    }

    @Override
    public Mono<Void> execute(ChatInputInteractionEvent event, ApiClient apiClient) {
        logger.info("Executing '{}' command for user {}", getContext(), EventUtility.getInvokingUserTag(event));
        Mono<Boolean> userEphemeralMono = getEphemeralSetting(apiClient);
        return userEphemeralMono
                .flatMap( userEphemeral ->
                        apiClient.getActiveContainer().flatMap(activeContainer -> {
                            List<String> message = messageFormatter.formatActiveContainerReply(activeContainer);
                            return ReplyUtility.sendMultiPartReply(event, message, userEphemeral);
                        })
                )
                .contextWrite(ctx -> EventUtility.addUserContext(ctx, event.getInteraction().getUser()))
                .then();
    }
}
