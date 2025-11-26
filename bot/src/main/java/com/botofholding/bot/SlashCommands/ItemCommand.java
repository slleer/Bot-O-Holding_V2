package com.botofholding.bot.SlashCommands;

import com.botofholding.bot.Config.CommandConfig;
import com.botofholding.bot.SlashCommands.Parsers.ItemParser;
import com.botofholding.bot.Service.ApiClient;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class ItemCommand extends SubcommandDispatcherCommand<ItemParser> {

    protected ItemCommand(Collection<ItemParser> subParsers, ApiClient apiClient, CommandConfig commandConfig) {
        super(commandConfig.getCmdItem(), subParsers, apiClient);
    }
}
