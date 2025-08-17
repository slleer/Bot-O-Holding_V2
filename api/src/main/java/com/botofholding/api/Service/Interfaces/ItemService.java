package com.botofholding.api.Service.Interfaces;

import com.botofholding.contract.DTO.Response.AutoCompleteDto;
import com.botofholding.contract.DTO.Response.ItemSummaryDto;
import com.botofholding.api.Domain.Entity.Owner;

import java.util.List;

public interface ItemService {

    ItemSummaryDto findItemById(Long id);
    List<ItemSummaryDto> findItemsForPrincipalAndActor(String name, Owner actor, Owner principal);
    List<AutoCompleteDto> autocompleteItemsForPrincipalAndActor(String prefix, Owner actor, Owner principal);
}
