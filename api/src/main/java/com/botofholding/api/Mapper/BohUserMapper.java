package com.botofholding.api.Mapper;

import com.botofholding.contract.DTO.Request.UserRequestDto;
import com.botofholding.contract.DTO.Response.BohUserSummaryDto;
import com.botofholding.contract.DTO.Response.BohUserTestResponseDto;
import com.botofholding.contract.DTO.Response.BohUserWithAllContainersDto;
import com.botofholding.contract.DTO.Response.BohUserWithPrimaryContainerDto;
import com.botofholding.api.Domain.Entity.BohUser;
import org.mapstruct.*;

@Mapper(componentModel = "spring"
        , unmappedTargetPolicy = ReportingPolicy.IGNORE
        , uses = {ContainerMapper.class},
        builder = @Builder)
public interface BohUserMapper {


    BohUser requestToEntity(UserRequestDto request);

    @Mapping(source = "discordId", target = "discordId") // Explicitly map the discord ID
    BohUserSummaryDto toSummaryDto(BohUser entity);


    // Mapping to DTO with primary container (full object)
    @Mapping(source = "discordId", target = "discordId")
    @Mapping(source = "primaryContainer", target = "primaryContainer") // Map the full object
    BohUserWithPrimaryContainerDto toWithPrimaryContainerDto(BohUser entity);

    // Mapping to DTO with all containers
    @Mapping(source = "discordId", target = "discordId")
    @Mapping(source = "containers", target = "containers") // Map the collection
    BohUserWithAllContainersDto toWithAllContainersDto(BohUser entity);


    BohUser dtoToEntity(BohUserSummaryDto dto);
    UserRequestDto entityToRequest(BohUser entity);

    void updateEntityFromUpdateRequest(UserRequestDto updatedUser, @MappingTarget BohUser existingUser);

    BohUser dtoToEntity(BohUserTestResponseDto foundUser);
}
