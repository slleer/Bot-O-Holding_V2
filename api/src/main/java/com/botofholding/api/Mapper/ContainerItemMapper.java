package com.botofholding.api.Mapper;

import com.botofholding.contract.DTO.Response.AutoCompleteDto;
import com.botofholding.contract.DTO.Response.ContainerItemSummaryDto;
import com.botofholding.contract.DTO.Response.AutoCompleteProjection;
import com.botofholding.api.Domain.Entity.ContainerItem;
import org.mapstruct.Mapper;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ContainerItemMapper {

    /**
     * The primary mapping method for a ContainerItem, including one level of its children.
     * This method is named so it can be explicitly used by collection mappers.
     */
    @Named("summary")
    @Mapping(source = "containerItemId", target = "containerItemId")
    @Mapping(source = "item.itemId", target = "itemId")
    @Mapping(source = "item.itemName", target = "itemName")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "userNote", target = "userNote")
    @Mapping(source = "lastModifiedDateTime", target = "lastModified")
    @Mapping(source = "parent.containerItemId", target = "parentId")
    @Mapping(source = "containerItem", target = "path", qualifiedByName = "mapItemPath")
    ContainerItemSummaryDto toSummaryDto(ContainerItem containerItem);

    @Mapping(source = "containerItem", target = "label", qualifiedByName = "mapItemPath")
    @Mapping(source = "containerItemId", target = "id")
    @Mapping(source = "containerItem", target = "description", qualifiedByName = "mapDescription")
    AutoCompleteDto toAutoCompleteDto(ContainerItem containerItem);

    /**
     * Maps a collection of ContainerItems using the full "summary" method for each item.
     * This is used by ContainerMapper to map the top-level items in a container.
     */
    @Named("toSummaryDtoList")
    @IterableMapping(qualifiedByName = "summary")
    List<ContainerItemSummaryDto> toSummaryDtoList(Set<ContainerItem> containerItems);

    AutoCompleteDto toAutoCompleteDto(AutoCompleteProjection projection);

    @Named("mapItemPath")
    default String mapItemPath(ContainerItem containerItem) {
        if (containerItem == null) {
            return ""; // Or some default like "N/A"
        }
        // [IMPROVEMENT] Use an iterative approach to prevent potential StackOverflowError on deep hierarchies.
        LinkedList<String> path = new LinkedList<>();
        ContainerItem current = containerItem;
        while (current != null) {
            path.addFirst(current.getItem() != null ? current.getItem().getItemName() : "Unnamed Item");
            current = current.getParent();
        }
        return String.join(" > ", path);
    }

    /**
     * Creates a description for an autocomplete entry, combining containerItemId, quantity and user note.
     * e.g., "[343] (x5) A special potion" or "(10)" if no note exists.
     * @param containerItem The item to describe.
     * @return A formatted description string.
     */
    @Named("mapDescription")
    default String mapDescription(ContainerItem containerItem) {
        if (containerItem == null) {
            return "";
        }
        StringBuilder description = new StringBuilder();
        description.append("[").append(containerItem.getContainerItemId()).append("] ");
        if (containerItem.getQuantity() != null) {
            description.append("x").append(containerItem.getQuantity());
        }
        String note = containerItem.getUserNote();
        if (note != null && !note.isBlank()) {
            if (!description.isEmpty()) {
                description.append(" ");
            }
            description.append(note);
        }
        return description.toString();
    }
}