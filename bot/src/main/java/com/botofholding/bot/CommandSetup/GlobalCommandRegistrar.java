package com.botofholding.bot.CommandSetup;

import com.botofholding.bot.Config.CommandConfig;
import discord4j.discordjson.json.ApplicationCommandRequest;
import discord4j.rest.RestClient;
import discord4j.rest.service.ApplicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GlobalCommandRegistrar implements ApplicationRunner {
    private final Logger logger = LoggerFactory.getLogger(GlobalCommandRegistrar.class);

    private final RestClient client;
    private final CommandFactory commandFactory;
    private final CommandConfig commandConfig;

    public GlobalCommandRegistrar(RestClient client, CommandFactory commandFactory, CommandConfig commandConfig) {
        this.client = client;
        this.commandFactory = commandFactory;
        this.commandConfig = commandConfig;
    }

    @Override
    public void run(ApplicationArguments args) {
        String theme = commandConfig.getTheme();
        logger.info("Beginning command registration process for theme: '{}'", theme);

        final ApplicationService applicationService = client.getApplicationService();
        final long applicationId = client.getApplicationId().block();

        // Get all commands directly from the factory.
        List<ApplicationCommandRequest> commands = commandFactory.buildAllCommands();

        if (commands.isEmpty()) {
            logger.warn("No commands were built by the factory. No commands will be registered.");
            return;
        }

        logger.info("Registering {} global application command(s) for theme '{}'...", commands.size(), theme);
        applicationService.bulkOverwriteGlobalApplicationCommand(applicationId, commands)
                .doOnNext(cmd -> logger.debug("Successfully registered command: {}", cmd.name()))
                .doOnComplete(() -> logger.info("Successfully registered all global commands for theme '{}'", theme))
                .doOnError(e -> logger.error("Failed to register global commands for theme '{}'", theme, e))
                .subscribe();
    }
}
