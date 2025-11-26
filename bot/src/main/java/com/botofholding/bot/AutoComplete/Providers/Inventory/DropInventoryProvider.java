package com.botofholding.bot.AutoComplete.Providers.Inventory;

import com.botofholding.bot.AutoComplete.Providers.AbstractActiveItemNameProvider;
import com.botofholding.bot.AutoComplete.Providers.InventoryProvider;
import com.botofholding.bot.Config.CommandConfig;
import com.botofholding.bot.Service.ApiClient;
import org.springframework.stereotype.Component;

@Component
public class DropInventoryProvider extends AbstractActiveItemNameProvider implements InventoryProvider {

    public DropInventoryProvider(ApiClient apiClient, CommandConfig commandConfig) {
        super(apiClient, commandConfig);
    }

    @Override
    public String getSubCommandName() {
        return commandConfig.getSubcmdInventoryDrop();
    }

}
