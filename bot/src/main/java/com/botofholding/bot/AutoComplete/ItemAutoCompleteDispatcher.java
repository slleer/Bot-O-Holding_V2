package com.botofholding.bot.AutoComplete;

import com.botofholding.bot.AutoComplete.Providers.ItemProvider;
import com.botofholding.bot.Config.CommandConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class ItemAutoCompleteDispatcher extends AutoCompleteDispatcher<ItemProvider> {
    @Autowired
    public ItemAutoCompleteDispatcher(Collection<ItemProvider> providers, CommandConfig commandConfig) {
        // This dispatcher handles all autocomplete options for the /item command.
        super(commandConfig.getCmdItem(), providers);
    }
}
