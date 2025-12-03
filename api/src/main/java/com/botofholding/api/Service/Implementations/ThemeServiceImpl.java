package com.botofholding.api.Service.Implementations;

import com.botofholding.api.Domain.Entity.Theme;
import com.botofholding.api.Repository.ThemeRepository;
import com.botofholding.api.Service.Interfaces.ThemeService;
import com.botofholding.contract.DTO.Request.ThemeRequestDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class ThemeServiceImpl implements ThemeService {

    private static final Logger logger = LoggerFactory.getLogger(ThemeServiceImpl.class);
    private final ThemeRepository themeRepository;

    public ThemeServiceImpl(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    @Override
    @Transactional
    public Theme upsertTheme(ThemeRequestDto themeRequestDto) {
        logger.info("Attempting to upsert theme with name: {}", themeRequestDto.getThemeName());
        return themeRepository.findByThemeName(themeRequestDto.getThemeName())
                .map(theme -> {
                    // Only update if the description has actually changed.
                    if (!Objects.equals(theme.getThemeDescription(), themeRequestDto.getThemeDescription())) {
                        logger.info("Theme '{}' found. Updating description.", theme.getThemeName());
                        theme.setThemeDescription(themeRequestDto.getThemeDescription());
                        return themeRepository.save(theme);
                    } else {
                        logger.info("Theme '{}' found, and description is already up-to-date. No changes needed.", theme.getThemeName());
                        return theme; // Return the existing, unchanged theme.
                    }
                })
                .orElseGet(() -> {
                    logger.info("Theme not found. Creating new theme with name: {}", themeRequestDto.getThemeName());
                    Theme newTheme = new Theme();
                    newTheme.setThemeName(themeRequestDto.getThemeName());
                    newTheme.setThemeDescription(themeRequestDto.getThemeDescription());
                    return themeRepository.save(newTheme);
                });
    }
}
