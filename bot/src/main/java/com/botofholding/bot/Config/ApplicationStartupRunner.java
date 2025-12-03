package com.botofholding.bot.Config;

import com.botofholding.bot.Service.ApiClient;
import com.botofholding.contract.DTO.Request.ThemeRequestDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

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
        logger.info("------------------------------------------------------------------------");

        logger.info("  Application is ready. Starting initialization tasks...");

        // 1. Initialize the API client to get the token.
        // This is a blocking call, but it's safe here as the application is already running.
        try {
            apiClient.initialize();
            logger.info("  API Client initialized successfully.");
        } catch (Exception e) {
            logger.error("FATAL: API Client initialization failed. The application may not function correctly.", e);
            // Depending on the desired behavior, you might want to terminate the application.
            // For now, we'll log the error and continue.
            return; // Stop further processing if token fetching fails.
        }

        // 2. Upsert the theme, which is now guaranteed to be loaded into commandConfig.
        ThemeRequestDto themeRequestDto = new ThemeRequestDto();
        themeRequestDto.setThemeName(commandConfig.getTheme());
        themeRequestDto.setThemeDescription(commandConfig.getThemeDescription());

        if (themeRequestDto.getThemeName() == null) {
            logger.error("Theme name is null after application startup. Cannot upsert theme. Check your application.properties.");
            return;
        }

        logger.info("  Upserting theme: '{}'", themeRequestDto.getThemeName());
        apiClient.upsertTheme(themeRequestDto)
                .doOnSuccess(themeDto -> logger.info("Successfully upserted theme: '{}'", themeDto.getThemeName()))
                .doOnError(error -> logger.error("Failed to upsert theme after startup.", error))
                .subscribe();

        logger.info("========================================================================");
    }
}
