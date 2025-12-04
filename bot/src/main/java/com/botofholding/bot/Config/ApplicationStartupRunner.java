package com.botofholding.bot.Config;

import com.botofholding.bot.Service.ApiClient;
import com.botofholding.contract.DTO.Request.ThemeRequestDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Properties;

@Component
public class ApplicationStartupRunner implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationStartupRunner.class);

    private final ApiClient apiClient;
    private final CommandConfig commandConfig;
    private final ApplicationInfoService applicationInfoService;

    public ApplicationStartupRunner(ApiClient apiClient, CommandConfig commandConfig, ApplicationInfoService applicationInfoService) {
        this.apiClient = apiClient;
        this.commandConfig = commandConfig;
        this.applicationInfoService = applicationInfoService;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        logger.info("========================================================================");
        logger.info("  Bot-O-Holding Bot ({})", applicationInfoService.getName());
        logger.info("  Version:     {} (Build: {})", applicationInfoService.getVersion(), applicationInfoService.getBuildTimestamp());
        logger.info("========================================================================");

        logger.info("  Application is ready. Starting initialization tasks...");

        // 1. Initialize the API client to get the token.
        try {
            apiClient.initialize();
            logger.info("API Client initialized successfully.");
        } catch (Exception e) {
            logger.error("FATAL: API Client initialization failed. The application may not function correctly.", e);
            return; // Stop further processing if token fetching fails.
        }

        // 2. Get the active theme name from the loaded CommandConfig.
        String activeThemeName = commandConfig.getTheme();
        if (activeThemeName == null || activeThemeName.isBlank()) {
            logger.error("Active theme name is not configured. Cannot upsert theme. Check CommandConfig defaults and application properties.");
            return;
        }

        // 3. Load all theme definitions from the shared properties file to find the description.
        Properties themeProps = new Properties();
        try (InputStream themeInputStream = new ClassPathResource("themes.properties").getInputStream()) {
            themeProps.load(themeInputStream);
        } catch (Exception e) {
            logger.error("FATAL: Could not load themes.properties from shared-config. Aborting theme upsert.", e);
            return;
        }

        // 4. Look up the description for the active theme.
        String themeDescription = themeProps.getProperty(activeThemeName + ".description");
        if (themeDescription == null) {
            logger.warn("Could not find a description for theme '{}' in themes.properties. Upserting with no description.", activeThemeName);
            themeDescription = ""; // Default to empty string if not found
        }

        // 5. Build the DTO and upsert the theme.
        ThemeRequestDto themeRequestDto = new ThemeRequestDto();
        themeRequestDto.setThemeName(activeThemeName);
        themeRequestDto.setThemeDescription(themeDescription);

        logger.info("Upserting active theme: '{}'", themeRequestDto.getThemeName());
        apiClient.upsertTheme(themeRequestDto)
                .doOnSuccess(themeDto -> logger.info("Successfully upserted theme: '{}'", themeDto.getThemeName()))
                .doOnError(error -> logger.error("Failed to upsert theme after startup.", error))
                .subscribe();
    }
}
