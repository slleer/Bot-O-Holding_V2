package com.botofholding.bot.AutoComplete.Providers.Inventory;

import com.botofholding.bot.AutoComplete.Providers.AbstractActiveParentItemNameProvider;
import com.botofholding.bot.AutoComplete.Providers.InventoryProvider;
import com.botofholding.bot.Config.CommandConfig;
import com.botofholding.bot.Service.ApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InsideModifyInventoryProvider extends AbstractActiveParentItemNameProvider implements InventoryProvider {

    @Autowired
    public InsideModifyInventoryProvider(ApiClient apiClient, CommandConfig commandConfig) {
        super(apiClient ,commandConfig);
    }

    @Override
    public String getSubCommandName() {
        return commandConfig.getSubcmdInventoryModify();
    }

    @Override
    public String getOptionName() {
        return commandConfig.getOptionInventoryModifyMoveInside();
    }
}
