package com.botofholding.bot.CommandSetup;

import discord4j.common.JacksonResources;
import discord4j.discordjson.json.ApplicationCommandRequest;
import discord4j.rest.RestClient;
import discord4j.rest.service.ApplicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class GlobalCommandRegistrar implements ApplicationRunner {
    private final Logger logger = LoggerFactory.getLogger(GlobalCommandRegistrar.class);

    private final RestClient client;

    @Value("${bot.theme:generic}") // Injects the bot.theme property, defaults to "generic"
    private String theme;

    //Use the rest client provided by our Bean
    public GlobalCommandRegistrar(RestClient client) {
        this.client = client;
    }

    //This method will run only once on each start up and is automatically called with Spring so blocking is okay.
    @Override
    public void run(ApplicationArguments args) throws IOException {
        //Create an ObjectMapper that supported Discord4J classes
        final JacksonResources d4jMapper = JacksonResources.create();

        logger.info("Using '{}' theme for command registration.", theme);

        // Convenience variables for the sake of easier to read code below.
        PathMatchingResourcePatternResolver matcher = new PathMatchingResourcePatternResolver();
        final ApplicationService applicationService = client.getApplicationService();
        final long applicationId = client.getApplicationId().block();

        //Get our commands json from resources as command data
        List<ApplicationCommandRequest> commands = new ArrayList<>();
        String commandLocation = "commands/" + theme + "/*.json";
        logger.info("Loading command definitions from '{}'", commandLocation);
        for (Resource resource : matcher.getResources(commandLocation)) {
            try {
                ApplicationCommandRequest request = d4jMapper.getObjectMapper()
                        .readValue(resource.getInputStream(), ApplicationCommandRequest.class);
                commands.add(request);
                logger.debug("Loaded command definition from: {}", resource.getFilename());
            } catch (IOException e) {
                logger.error("Failed to read or parse command definition from resource: {}", resource.getFilename(), e);
                // Decide if you want to continue or rethrow to stop the application
                // For now, it will skip the problematic file and continue
            }
        }

        if (commands.isEmpty()) {
            logger.warn("No command definitions found in '{}'. No commands will be registered.", commandLocation);
            return;
        }

        logger.info("Registering {} global application command(s)...", commands.size());
        applicationService.bulkOverwriteGlobalApplicationCommand(applicationId, commands)
                .doOnNext(ignore -> logger.info("Successfully registered Global Commands for theme '{}'", theme))
                .doOnError(e -> logger.error("Failed to register global commands for theme '{}'", theme, e))
                .subscribe();
    }
}
