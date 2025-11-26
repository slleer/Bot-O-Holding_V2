package com.botofholding.bot.AutoComplete.Providers.Inventory;

import com.botofholding.bot.AutoComplete.Providers.AbstractItemNameProvider;
import com.botofholding.bot.AutoComplete.Providers.InventoryProvider;
import com.botofholding.bot.Config.CommandConfig;
import com.botofholding.bot.Service.ApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ItemAddInventoryProvider extends AbstractItemNameProvider implements InventoryProvider {

    @Autowired
    public ItemAddInventoryProvider(ApiClient apiClient, CommandConfig commandConfig) {
        super(apiClient ,commandConfig);
    }

    @Override
    public String getSubCommandName() {
        return commandConfig.getSubcmdInventoryAdd();
    }

    @Override
    public String getOptionName() {
        return commandConfig.getOptionItem();
    }
}
