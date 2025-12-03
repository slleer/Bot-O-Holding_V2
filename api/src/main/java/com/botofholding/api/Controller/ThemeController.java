package com.botofholding.api.Controller;

import com.botofholding.api.Domain.Entity.Theme;
import com.botofholding.api.Mapper.ThemeMapper;
import com.botofholding.api.Service.Interfaces.ThemeService;
import com.botofholding.contract.DTO.Request.ThemeRequestDto;
import com.botofholding.contract.DTO.Response.StandardApiResponse;
import com.botofholding.contract.DTO.Response.ThemeDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/themes")
public class ThemeController {

    private static final Logger logger = LoggerFactory.getLogger(ThemeController.class);
    private final ThemeService themeService;
    private final ThemeMapper themeMapper;

    public ThemeController(ThemeService themeService, ThemeMapper themeMapper) {
        this.themeService = themeService;
        this.themeMapper = themeMapper;
    }

    @PutMapping
    public ResponseEntity<StandardApiResponse<ThemeDto>> upsertTheme(@RequestBody ThemeRequestDto themeRequestDto) {
        logger.info("Attempting to upsert theme with name: {}", themeRequestDto.getThemeName());
        Theme theme = themeService.upsertTheme(themeRequestDto);
        ThemeDto themeDto = themeMapper.toDto(theme);
        StandardApiResponse<ThemeDto> response = new StandardApiResponse<>(true, "Theme upserted successfully", themeDto);
        return ResponseEntity.ok(response);
    }
}
