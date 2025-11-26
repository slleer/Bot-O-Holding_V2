package com.botofholding.bot.SlashCommands;

import com.botofholding.bot.Config.CommandConfig;
import com.botofholding.bot.SlashCommands.Parsers.InventoryParser;
import com.botofholding.bot.Service.ApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class InventoryCommand extends SubcommandDispatcherCommand<InventoryParser> {

    @Autowired
    public InventoryCommand(Collection<InventoryParser> inventoryParsers, ApiClient apiClient, CommandConfig commandConfig) {
        super(commandConfig.getCmdInventory(), inventoryParsers, apiClient);
    }
}
