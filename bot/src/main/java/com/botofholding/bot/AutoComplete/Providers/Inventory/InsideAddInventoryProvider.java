package com.botofholding.bot.AutoComplete.Providers.Inventory;

import com.botofholding.bot.AutoComplete.Providers.AbstractActiveParentItemNameProvider;
import com.botofholding.bot.AutoComplete.Providers.InventoryProvider;
import com.botofholding.bot.Config.CommandConfig;
import com.botofholding.bot.Service.ApiClient;
import org.springframework.stereotype.Component;

@Component
public class InsideAddInventoryProvider extends AbstractActiveParentItemNameProvider implements InventoryProvider {

    public InsideAddInventoryProvider(ApiClient apiClient, CommandConfig commandConfig) {
        super(apiClient ,commandConfig);
    }

    @Override
    public String getSubCommandName() {
        return commandConfig.getSubcmdInventoryAdd();
    }

    @Override
    public String getOptionName() {
        return commandConfig.getOptionInventoryAddParent();
    }

}
