package com.botofholding.bot.AutoComplete.Providers.Item;

import com.botofholding.bot.AutoComplete.Providers.AbstractItemNameProvider;
import com.botofholding.bot.AutoComplete.Providers.ItemProvider;
import com.botofholding.bot.Config.CommandConfig;
import com.botofholding.bot.Service.ApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FindItemProvider extends AbstractItemNameProvider implements ItemProvider {

    @Autowired
    public FindItemProvider(ApiClient apiClient, CommandConfig commandConfig) {
        super(apiClient ,commandConfig);
    }

    @Override
    public String getOptionName() {
        return commandConfig.getOptionName();
    }

    @Override
    public String getSubCommandName() {
        return commandConfig.getSubcmdItemFind();
    }

}
