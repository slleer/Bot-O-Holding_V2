package com.botofholding.bot.Config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "bot.commands")
@Data
public class CommandConfig {

    // --- Theme ---
    private String theme = "generic";
    private String themeDescription = "Theme for generic use [USERS, CONTAINERS, GROUPS, ITEMS]";

    // --- Reply Headers ---
    private String replyHeaderFoundContainer = "Found Container:";
    private String replyHeaderFoundContainers = "Found Containers:";
    private String replyHeaderUsingContainer = "Using Container:";
    private String replyHeaderNewContainer = "New Container:";
    private String replyHeaderActiveContainer = "Active Container:";
    private String replyHeaderUpdatedSettings = "Updated Settings:";
    private String replyHeaderFoundItem = "Found Item:";
    private String replyHeaderFoundItems = "Found Items:";
    private String replyHeaderDeletedEntity = "Deleted %s:";
    private String replyHeaderNoItemsFound = "No items found.";
    private String replyHeaderNoContainersFound = "No Container(s) Found.";


    // --- Generic, Reusable Option Names ---
    private String optionName = "name";
    private String optionDescription = "description";
    private String optionType = "group";
    private String optionServerOwned = "server-owned";
    private String optionId = "id";
    private String optionPrioritize = "prioritize";
    private String optionQuantity = "quantity";
    private String optionNote = "note";
    private int discordChoiceLimit = 25;

    // --- Generic, Reusable Entity based options ---
    private String optionItem = "item";

    // --- Top-Level Command Names & Descriptions ---
    private String cmdUser = "user";
    private String cmdUserDescription = "user commands";
    private String cmdContainer = "container";
    private String cmdContainerDescription = "container commands";
    private String cmdItem = "item";
    private String cmdItemDescription = "item commands";
    private String cmdInventory = "inventory";
    private String cmdInventoryDescription = "Manage items within your active container";

    // --- User Command ---
    private String subcmdUserGet = "get";
    private String subcmdUserGetDescription = "Get user details";
    private String contextUserGet = "user-get";
    private String subcmdUserUpdate = "update";
    private String subcmdUserUpdateDescription = "Update user details";
    private String contextUserUpdate = "user-update";
    private String subcmdUserSettings = "settings";
    private String subcmdUserSettingsDescription = "Get or update User settings, blank fields will be ignored";
    private String contextUserSettings = "user-settings";
    private String optionUserSettingsHideContainer = "hide-container";
    private String optionUserSettingsHideContainerDescription = "If false, container commands will be visible to the server. Otherwise, only you can see them.";
    private String optionUserSettingsHideUser = "hide-user";
    private String optionUserSettingsHideUserDescription = "If false, user commands will be visible to the server. Otherwise, only you can see them.";
    private String optionUserSettingsHideItem = "hide-item";
    private String optionUserSettingsHideItemDescription = "If false, item commands will be visible to the server. Otherwise, only you can see them.";

    // --- Container Command ---
    private String subcmdContainerAdd = "new";
    private String subcmdContainerAddDescription = "Create a new container";
    private String contextContainerAdd = "container-new";
    private String optionContainerAddNameDescription = "Name of the container. EX: Book Recommendations.";
    private String optionContainerAddDescriptionDescription = "Description of the container.";
    private String optionContainerAddTypeDescription = "What group does this container belong to, if any.";
    private String optionContainerAddServerOwnedDescription = "if true, container will be owned by the server and available to all members";
    private String optionContainerAddSetAsActive = "set-as-active";
    private String optionContainerAddSetAsActiveDescription = "Set this container as active";
    private String subcmdContainerActivate = "set-active";
    private String subcmdContainerActivateDescription = "Set this container as active";
    private String contextContainerActivate = "container-set-active";
    private String optionContainerActivateNameDescription = "Name of the container. EX: Book Recommendations.";
    private String optionContainerActivatePrioritizeDescription = "Choose which to prioritize in the event of a collision. Use if autocomplete fails.";
    private String subcmdContainerFindActive = "get-active";
    private String subcmdContainerFindActiveDescription = "Returns this user's active container";
    private String contextContainerFindActive = "container-get-active";
    private String subcmdContainerFind = "find";
    private String subcmdContainerFindDescription = "Find container";
    private String contextContainerFind = "container-find";
    private String optionContainerFindNameDescription = "Name of the container. EX: Book Recommendations. Returns all containers for user if blank";
    private String subcmdContainerDelete = "delete";
    private String subcmdContainerDeleteDescription = "Delete a container";
    private String contextContainerDelete = "container-delete";
    private String optionContainerDeleteNameDescription = "Name of the container to delete";
    private String optionContainerDeleteIdDescription = "The id of the container to delete";

    // --- Item Command ---
    private String subcmdItemFind = "find";
    private String subcmdItemFindDescription = "Find an item";
    private String contextItemFind = "item-find";
    private String optionItemFindNameDescription = "Name of the item";
    private String subcmdItemNew = "new";
    private String contextItemNew = "item-new";

    // --- Inventory Command ---
    private String subcmdInventoryAdd = "add";
    private String subcmdInventoryAddDescription = "Add an item to your active container";
    private String contextInventoryAdd = "inventory-add";
    private String optionInventoryAddItemDescription = "item to add";
    private String optionInventoryAddQuantityDescription = "Quantity of the item to add. Defaults to 1";
    private String optionInventoryAddNoteDescription = "Add a note to the item (max 350 chars).";
    private String optionInventoryAddParent = "put-inside";
    private String optionInventoryAddParentDescription = "Put the added item inside this item, if selected.";
    private String subcmdInventoryDrop = "drop";
    private String subcmdInventoryDropDescription = "Drop an item from your active container";
    private String contextInventoryDrop = "inventory-drop";
    private String optionInventoryDropItemDescription = "item to drop";
    private String optionInventoryDropQuantityDescription = "Quantity of the item to drop. Defaults to 1";
    private String optionInventoryDropDropChildren = "drop-contents";
    private String optionInventoryDropDropChildrenDescription = "If true and item is container, contents will be dropped with item.";
    private String subcmdInventoryModify = "edit";
    private String subcmdInventoryModifyDescription = "Edit one or more properties of an item in your active container";
    private String contextInventoryModify = "inventory-modify";
    private String optionInventoryModifyItemDescription = "Item to edit";
    private String optionInventoryModifyNoteDescription = "Replace existing note or remove by providing an empty string (max 350 characters).";
    private String optionInventoryModifyMoveToRoot = "move-to-root";
    private String optionInventoryModifyMoveToRootDescription = "Moves item to root of container. Don't use with 'move-inside' option";
    private String optionInventoryModifyMoveInside = "move-inside";
    private String optionInventoryModifyMoveInsideDescription = "Moves item inside this item. Don't use with 'move-to-root' option";
}
