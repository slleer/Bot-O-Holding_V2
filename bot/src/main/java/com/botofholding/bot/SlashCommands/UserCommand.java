package com.botofholding.bot.SlashCommands;

import com.botofholding.bot.Config.CommandConfig;
import com.botofholding.bot.SlashCommands.Parsers.UserParser;
import com.botofholding.bot.Service.ApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class UserCommand extends SubcommandDispatcherCommand<UserParser> {

    @Autowired
    public UserCommand(Collection<UserParser> userParsers, ApiClient apiClient, CommandConfig commandConfig) {
        super(commandConfig.getCmdUser(), userParsers, apiClient); // Call the super constructor
    }

}
