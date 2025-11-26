package com.botofholding.bot.AutoComplete.Providers;

import com.botofholding.bot.Config.CommandConfig;
import com.botofholding.bot.Domain.DTOs.Request.AutoCompleteRequestDto;
import com.botofholding.contract.DTO.Response.AutoCompleteDto;
import com.botofholding.bot.Service.ApiClient;
import reactor.core.publisher.Mono;

import java.util.List;

public abstract class AbstractActiveParentItemNameProvider implements AutoCompleteProvider{

    private final ApiClient apiClient;
    protected final CommandConfig commandConfig;

    public AbstractActiveParentItemNameProvider(ApiClient apiClient, CommandConfig commandConfig) {
        this.apiClient = apiClient;
        this.commandConfig = commandConfig;
    }

    @Override
    public CommandConfig getCommandConfig() {
        return commandConfig;
    }

    @Override
    public Mono<List<AutoCompleteDto>> fetchSuggestions(AutoCompleteRequestDto request) {
        return apiClient.autocompleteParentActiveContainerItems(request.getPrefix());
    }
}
