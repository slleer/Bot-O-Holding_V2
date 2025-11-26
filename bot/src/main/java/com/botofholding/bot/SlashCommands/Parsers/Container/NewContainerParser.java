package com.botofholding.bot.SlashCommands.Parsers.Container;

import com.botofholding.bot.Config.CommandConfig;
import com.botofholding.contract.DTO.Request.ContainerRequestDto;
import com.botofholding.contract.DTO.Response.ContainerSummaryDto;
import com.botofholding.bot.Domain.Entities.TargetOwner;
import com.botofholding.bot.SlashCommands.Parsers.ContainerParser;
import com.botofholding.bot.SlashCommands.Parsers.RequestBodyParser;
import com.botofholding.bot.Service.ApiClient;
import com.botofholding.bot.Utility.*;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

@Component
public class NewContainerParser implements ContainerParser, RequestBodyParser<ContainerRequestDto> {

    private static final Logger logger = LoggerFactory.getLogger(NewContainerParser.class);
    private final CommandConfig commandConfig;

    public NewContainerParser(CommandConfig commandConfig) {
        this.commandConfig = commandConfig;
    }

    @Override
    public String getSubCommandName() {
        return commandConfig.getSubcmdContainerAdd();
    }

    @Override
    public String getContext() {
        return commandConfig.getContextContainerAdd();
    }

    @Override
    public Mono<Void> execute(ChatInputInteractionEvent event, ApiClient apiClient) {
        logger.info("Executing '{}' command for user {}", getContext(), EventUtility.getInvokingUserTag(event));

        // 1. Determine the target owner (user or guild) based on the 'server-owned' option.
        OwnerTypeExtractor extractor = EventUtility::getOwnerTypeFromSingleChoice;
        Mono<TargetOwner> targetOwnerMono = EventUtility.determineTargetOwner(event, getSubCommandName(), commandConfig.getOptionServerOwned(), extractor);

        // 2. Determine if the reply should be ephemeral. Guild-owned is always public.
        Mono<Boolean> useEphemeralMono = targetOwnerMono.flatMap(target -> {
            if ("GUILD".equals(target.ownerType())) {
                return Mono.just(false); // Guild-owned replies are never ephemeral.
            }
            // For user-owned, fetch their personal setting.
            return getEphemeralSetting(apiClient);
        });

        // 3. Build the request DTO from command options.
        Mono<ContainerRequestDto> dtoMono = buildRequestDto(event);

        // 4. Zip everything together and make the API call.
        return Mono.zip(targetOwnerMono, dtoMono, useEphemeralMono)
                .flatMap(tuple -> {
                    TargetOwner context = tuple.getT1();
                    ContainerRequestDto dto = tuple.getT2();
                    return apiClient.createContainer(dto, context.ownerId(), context.ownerType(), context.ownerName())
                            .map(container -> Tuples.of(tuple.getT3(), container)); // Pass ephemeral setting along
                })
                .flatMap(tuple -> {
                    boolean useEphemeral = tuple.getT1();
                    ContainerSummaryDto newContainer = tuple.getT2();
                    return ReplyUtility.sendMultiPartReply(event, MessageFormatter.formatAddContainerReply(newContainer), useEphemeral);
                })
                .contextWrite(ctx -> EventUtility.addUserContext(ctx, event.getInteraction().getUser()))
                .then();
    }

    @Override
    public Mono<ContainerRequestDto> buildRequestDto(ChatInputInteractionEvent event) {
        String subcommandName = getSubCommandName();

        // A required option that errors if not present. This is perfect.
        Mono<String> nameMono = EventUtility.getOptionValueAsString(event, subcommandName, commandConfig.getOptionName())
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Container 'name' is a required option.")));

        // This is a robust, explicit, and proven solution for your stream.
        Mono<String> descriptionMono = EventUtility.getOptionValueAsString(event, subcommandName, commandConfig.getOptionDescription())
                .defaultIfEmpty("");

        Mono<String> typeMono = EventUtility.getOptionValueAsString(event, subcommandName, commandConfig.getOptionType())
                .defaultIfEmpty("");

        Mono<Boolean> activeMono = Mono.just(EventUtility.getOptionValue(event, subcommandName, commandConfig.getOptionContainerAddSetAsActive()).isPresent());

        return Mono.zip(nameMono, descriptionMono, typeMono, activeMono)
                .map(tuple -> {
                    logger.debug("containerName: {}, containerDescription: {}, containerType: {}, active: {}", tuple.getT1(), tuple.getT2(), tuple.getT3(), tuple.getT4());
                    ContainerRequestDto dto = new ContainerRequestDto();
                    dto.setContainerName(tuple.getT1());
                    dto.setContainerDescription(tuple.getT2());
                    dto.setContainerTypeName(tuple.getT3());
                    dto.setActive(tuple.getT4());
                    return dto;
                });
    }
}
