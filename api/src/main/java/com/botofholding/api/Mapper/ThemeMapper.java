package com.botofholding.api.Mapper;

import com.botofholding.api.Domain.Entity.Theme;
import com.botofholding.contract.DTO.Response.ThemeDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ThemeMapper {
    ThemeDto toDto(Theme theme);
}
