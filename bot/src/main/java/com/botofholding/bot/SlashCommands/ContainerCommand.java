package com.botofholding.bot.SlashCommands;

import com.botofholding.bot.Config.CommandConfig;
import com.botofholding.bot.SlashCommands.Parsers.ContainerParser;
import com.botofholding.bot.Service.ApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class ContainerCommand extends SubcommandDispatcherCommand<ContainerParser> {

    @Autowired
    public ContainerCommand(Collection<ContainerParser> containerParsers, ApiClient apiClient, CommandConfig commandConfig) {
        super(commandConfig.getCmdContainer(), containerParsers, apiClient); // Call super constructor with "get" and GetParsers
    }

}
