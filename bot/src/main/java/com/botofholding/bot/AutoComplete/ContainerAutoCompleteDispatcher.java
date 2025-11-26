package com.botofholding.bot.AutoComplete;

import com.botofholding.bot.AutoComplete.Providers.ContainerProvider;
import com.botofholding.bot.Config.CommandConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class ContainerAutoCompleteDispatcher extends AutoCompleteDispatcher<ContainerProvider> {
    @Autowired
    public ContainerAutoCompleteDispatcher(Collection<ContainerProvider> providers, CommandConfig commandConfig) {
        // This dispatcher handles all autocomplete options for the /container command.
        super(commandConfig.getCmdContainer(), providers);
    }
}
