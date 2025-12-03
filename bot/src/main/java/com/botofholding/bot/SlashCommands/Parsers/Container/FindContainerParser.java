package com.botofholding.bot.SlashCommands.Parsers.Container;

import com.botofholding.bot.Config.CommandConfig;
import com.botofholding.bot.Domain.Entities.OwnerContext;
import com.botofholding.bot.Domain.Entities.Reply;
import com.botofholding.bot.Exception.ReplyException;
import com.botofholding.bot.SlashCommands.Parsers.ByNameParser;
import com.botofholding.bot.Service.ApiClient;
import com.botofholding.bot.SlashCommands.Parsers.ContainerParser;
import com.botofholding.bot.Utility.MessageFormatter;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class FindContainerParser implements ContainerParser, ByNameParser {

    private static final Logger logger = LoggerFactory.getLogger(FindContainerParser.class);
    private final CommandConfig commandConfig;
    private final MessageFormatter messageFormatter;

    @Autowired
    public FindContainerParser(CommandConfig commandConfig, MessageFormatter messageFormatter) {
        this.commandConfig = commandConfig;
        this.messageFormatter = messageFormatter;
    }

    @Override
    public CommandConfig getCommandConfig() {
        return commandConfig;
    }

    @Override
    public String getSubCommandName() {
        return commandConfig.getSubcmdContainerFind();
    }

    @Override
    public String getContext() {
        return commandConfig.getContextContainerFind();
    }

    @Override
    public Mono<Reply> fetchByIdAndFormat(Long objectId, ApiClient apiClient, Mono<OwnerContext> ownerContextMono) {
        logger.debug("Autocomplete value detected. Fetching container by ID: {}", objectId);
        return ownerContextMono.flatMap(owner ->
                apiClient.getContainerById(objectId, owner.ownerId(), owner.ownerType(), owner.ownerName())
                        .map(messageFormatter::formatGetContainerReply)
                        .map(message -> new Reply(message, owner.useEphemeral()))
        );
    }

    @Override
    public Mono<Reply> fetchByNameAndFormat(ChatInputInteractionEvent event, String objectName, ApiClient apiClient, Mono<OwnerContext> ownerContextMono) {
        logger.debug("Manual input detected. Searching for container by name.");

        return ownerContextMono.flatMap(owner ->
                apiClient.findContainers(objectName, owner.ownerId(), owner.ownerType(), owner.ownerName(), commandConfig.getTheme())
                        .map(containerList -> {
                            if (containerList.isEmpty()) {
                                throw new ReplyException("Could not find a container with that name.");
                            }
                            return messageFormatter.formatContainerReply(containerList);
                        })
                        .map(message -> new Reply(message, owner.useEphemeral())));
    }

}
