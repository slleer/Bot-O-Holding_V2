package com.botofholding.bot.AutoComplete.Providers;

import com.botofholding.bot.Config.CommandConfig;
import com.botofholding.bot.Domain.DTOs.Request.AutoCompleteRequestDto;
import com.botofholding.contract.DTO.Response.AutoCompleteDto;
import com.botofholding.bot.Domain.Entities.TargetOwner;
import com.botofholding.bot.Service.ApiClient;
import reactor.core.publisher.Mono;

import java.util.List;

public abstract class AbstractContainerNameProvider implements AutoCompleteProvider {

    private final ApiClient apiClient;
    protected final CommandConfig commandConfig;

    public AbstractContainerNameProvider(ApiClient apiClient, CommandConfig commandConfig) {
        this.apiClient = apiClient;
        this.commandConfig = commandConfig;
    }

    @Override
    public CommandConfig getCommandConfig() {
        return commandConfig;
    }

    @Override
    public String getOptionName() {
        return commandConfig.getOptionName();
    }

    @Override
    public Mono<List<AutoCompleteDto>> fetchSuggestions(AutoCompleteRequestDto request) {
        TargetOwner targetOwner = request.getTargetOwner();
        return apiClient.autocompleteContainers(
                request.getPrefix(),
                targetOwner.ownerId(),
                targetOwner.ownerType(),
                targetOwner.ownerName()
        );
    }
}
