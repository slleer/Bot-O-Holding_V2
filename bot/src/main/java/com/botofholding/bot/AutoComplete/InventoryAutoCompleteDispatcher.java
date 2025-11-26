package com.botofholding.bot.AutoComplete;

import com.botofholding.bot.AutoComplete.Providers.InventoryProvider;
import com.botofholding.bot.Config.CommandConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class InventoryAutoCompleteDispatcher extends AutoCompleteDispatcher<InventoryProvider> {
    @Autowired
    public InventoryAutoCompleteDispatcher(Collection<InventoryProvider> providers, CommandConfig commandConfig) {
        // This dispatcher handles all autocomplete options for the /inventory command.
        super(commandConfig.getCmdInventory(), providers);
    }
}
