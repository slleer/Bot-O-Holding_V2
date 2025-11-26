package com.botofholding.bot.AutoComplete.Providers.Container;

import com.botofholding.bot.AutoComplete.Providers.AbstractContainerNameProvider;
import com.botofholding.bot.AutoComplete.Providers.ContainerProvider;
import com.botofholding.bot.Config.CommandConfig;
import com.botofholding.bot.Service.ApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FindContainerProvider extends AbstractContainerNameProvider implements ContainerProvider {

    @Autowired
    public FindContainerProvider(ApiClient apiClient, CommandConfig commandConfig) {
        super(apiClient ,commandConfig);
    }

    @Override
    public String getSubCommandName() {
        return commandConfig.getSubcmdContainerFind();
    }
}
