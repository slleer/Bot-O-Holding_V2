package com.botofholding.bot.Config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "bot.commands")
@Data
public class CommandConfig {

    // --- Generic, Reusable Option Names ---
    private String optionName;
    private String optionDescription;
    private String optionType;
    private String optionServerOwned;
    private String optionId;
    private String optionPrioritize;
    private String optionQuantity;
    private String optionNote;
    private int discordChoiceLimit;

    // --- Generic, Reusable Entity based options ---
    private String optionItem;

    // --- Top-Level Command Names & Descriptions ---
    private String cmdUser;
    private String cmdUserDescription;
    private String cmdContainer;
    private String cmdContainerDescription;
    private String cmdItem;
    private String cmdItemDescription;
    private String cmdInventory;
    private String cmdInventoryDescription;

    // --- User Command ---
    private String subcmdUserGet;
    private String subcmdUserGetDescription;
    private String contextUserGet;
    private String subcmdUserUpdate;
    private String subcmdUserUpdateDescription;
    private String contextUserUpdate;
    private String subcmdUserSettings;
    private String subcmdUserSettingsDescription;
    private String contextUserSettings;
    private String optionUserSettingsHideContainer;
    private String optionUserSettingsHideContainerDescription;
    private String optionUserSettingsHideUser;
    private String optionUserSettingsHideUserDescription;
    private String optionUserSettingsHideItem;
    private String optionUserSettingsHideItemDescription;

    // --- Container Command ---
    private String subcmdContainerAdd;
    private String subcmdContainerAddDescription;
    private String contextContainerAdd;
    private String optionContainerAddNameDescription;
    private String optionContainerAddDescriptionDescription;
    private String optionContainerAddTypeDescription;
    private String optionContainerAddServerOwnedDescription;
    private String optionContainerAddSetAsActive;
    private String optionContainerAddSetAsActiveDescription;
    private String subcmdContainerActivate;
    private String subcmdContainerActivateDescription;
    private String contextContainerActivate;
    private String optionContainerActivateNameDescription;
    private String optionContainerActivatePrioritizeDescription;
    private String subcmdContainerFindActive;
    private String subcmdContainerFindActiveDescription;
    private String contextContainerFindActive;
    private String subcmdContainerFind;
    private String subcmdContainerFindDescription;
    private String contextContainerFind;
    private String optionContainerFindNameDescription;
    private String subcmdContainerDelete;
    private String subcmdContainerDeleteDescription;
    private String contextContainerDelete;
    private String optionContainerDeleteNameDescription;
    private String optionContainerDeleteIdDescription;

    // --- Item Command ---
    private String subcmdItemFind;
    private String subcmdItemFindDescription;
    private String contextItemFind;
    private String optionItemFindNameDescription;
    private String subcmdItemNew;
    private String contextItemNew;

    // --- Inventory Command ---
    private String subcmdInventoryAdd;
    private String subcmdInventoryAddDescription;
    private String contextInventoryAdd;
    private String optionInventoryAddItemDescription;
    private String optionInventoryAddQuantityDescription;
    private String optionInventoryAddNoteDescription;
    private String optionInventoryAddParent;
    private String optionInventoryAddParentDescription;
    private String subcmdInventoryDrop;
    private String subcmdInventoryDropDescription;
    private String contextInventoryDrop;
    private String optionInventoryDropItemDescription;
    private String optionInventoryDropQuantityDescription;
    private String optionInventoryDropDropChildren;
    private String optionInventoryDropDropChildrenDescription;
    private String subcmdInventoryModify;
    private String subcmdInventoryModifyDescription;
    private String contextInventoryModify;
    private String optionInventoryModifyItemDescription;
    private String optionInventoryModifyNoteDescription;
    private String optionInventoryModifyMoveToRoot;
    private String optionInventoryModifyMoveToRootDescription;
    private String optionInventoryModifyMoveInside;
    private String optionInventoryModifyMoveInsideDescription;
}
