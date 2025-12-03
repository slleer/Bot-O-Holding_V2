package com.botofholding.api.Service.Interfaces;

import com.botofholding.api.Domain.Entity.Theme;
import com.botofholding.contract.DTO.Request.ThemeRequestDto;

public interface ThemeService {
    Theme upsertTheme(ThemeRequestDto themeRequestDto);
}
