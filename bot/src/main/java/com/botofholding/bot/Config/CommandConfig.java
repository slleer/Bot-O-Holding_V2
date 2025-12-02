package com.botofholding.bot.Config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "bot.commands")
@Data
public class CommandConfig {

    // --- Generic, Reusable Option Names ---
    private String optionName = "name";
    private String optionDescription = "description";
    private String optionType = "type";
    private String optionServerOwned = "server-owned";
    private String optionId = "id";
    private String optionPrioritize = "prioritize";
    private String optionQuantity = "quantity";
    private String optionNote = "note";
    private int discordChoiceLimit = 25;

    // --- Generic, Reusable Entity based options ---
    private String optionItem = "item";

    // --- Top-Level Command Names ---
    private String cmdUser = "user";
    private String cmdContainer = "container";
    private String cmdItem = "item";
    private String cmdInventory = "inventory";

    // --- User Command ---
    private String subcmdUserGet = "get";
    private String contextUserGet = "user-get";
    private String subcmdUserUpdate = "update";
    private String contextUserUpdate = "user-update";
    private String subcmdUserSettings = "settings";
    private String contextUserSettings = "user-settings";
    private String optionUserSettingsHideContainer = "hide-container";
    private String optionUserSettingsHideUser = "hide-user";
    private String optionUserSettingsHideItem = "hide-item";

    // --- Container Command ---
    private String subcmdContainerAdd = "new";
    private String contextContainerAdd = "container-new";
    private String optionContainerAddSetAsActive = "set-as-active";
    private String subcmdContainerActivate = "set-active";
    private String contextContainerActivate = "container-set-active";
    private String subcmdContainerFindActive = "find-active";
    private String contextContainerFindActive = "container-find-active";
    private String subcmdContainerFind = "find";
    private String contextContainerFind = "container-find";
    private String subcmdContainerDelete = "delete";
    private String contextContainerDelete = "container-delete";

    // --- Item Command ---
    private String subcmdItemFind = "find";
    private String contextItemFind = "item-find";
    private String subcmdItemNew = "new";
    private String contextItemNew = "item-new";

    // --- Inventory Command ---
    private String subcmdInventoryAdd = "add";
    private String contextInventoryAdd = "inventory-add";
    private String optionInventoryAddParent = "put-inside";
    private String subcmdInventoryDrop = "drop";
    private String contextInventoryDrop = "inventory-drop";
    private String optionInventoryDropDropChildren = "drop-contents";
    private String subcmdInventoryModify = "edit";
    private String contextInventoryModify = "inventory-modify";
    private String optionInventoryModifyMoveToRoot = "move-to-root";
    private String optionInventoryModifyMoveInside = "move-inside";
}
