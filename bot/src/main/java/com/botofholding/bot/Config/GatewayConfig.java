package com.botofholding.bot.Config;

import com.botofholding.bot.EventListeners.EventListener;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.Event;
import discord4j.core.object.presence.ClientPresence;
import discord4j.gateway.intent.Intent;
import discord4j.gateway.intent.IntentSet;
import lombok.extern.log4j.Log4j2;
import discord4j.rest.RestClient;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Log4j2 // Lombok's annotation for SLF4J logging, provides 'log'
@Configuration
public class GatewayConfig {

    @Value("${bot_token}")
    private String token;

    @Bean
    public <T extends Event> GatewayDiscordClient gatewayDiscordClient(List<EventListener<T>> eventListeners) {
        GatewayDiscordClient client;

        try {
            log.info("Attempting to connect to Discord with token...");
            client = DiscordClientBuilder.create(token)
                    .build()
                    .gateway()
                    .setEnabledIntents(IntentSet.of(
                            Intent.GUILD_MEMBERS, Intent.GUILD_PRESENCES, Intent.DIRECT_MESSAGES,
                            Intent.DIRECT_MESSAGE_TYPING, Intent.DIRECT_MESSAGE_REACTIONS, Intent.GUILD_MESSAGES))
                    .setInitialPresence(ignore -> ClientPresence.online(
                            //ClientActivity.listening(" for loot drops")
                    ))
                    .login()
                    .block();

            if (client == null) {
                // This should ideally not happen if .block() throws on failure, but as a safeguard:
                log.error("Discord client login .block() returned null. This is unexpected.");
                throw new BeanCreationException("Failed to initialize Discord client: login returned null.");
            }
            log.info("Successfully connected to Discord and obtained GatewayDiscordClient.");

            log.info("Registering {} event listeners...", eventListeners.size());
            for (EventListener<T> listener : eventListeners) {
                try {
                    client.on(listener.getEventType())
                            .flatMap(listener::execute)
                            .onErrorResume(error -> {
                                // Log context from the listener itself if possible, then call its handler
                                log.error("Error during execution of listener for {}: {}",
                                        listener.getEventType().getSimpleName(), error.getMessage());
                                return listener.handleErrors(error);
                            })
                            .subscribe(null, error -> {
                                // General subscription error (should ideally be caught by onErrorResume)
                                log.error("Unhandled error in event subscription for {}: {}",
                                        listener.getEventType().getSimpleName(), error.getMessage(), error);
                            });
                    log.debug("Successfully registered listener for event type: {}", listener.getEventType().getSimpleName());
                } catch (Exception e) {
                    // Catch errors during individual listener setup (e.g., if getEventType() is problematic)
                    log.error("Failed to set up event listener for {}: {}. This listener will not be active.",
                            // Avoid calling listener.getEventType() again if it might be the source of the error
                            (listener != null ? listener.getClass().getSimpleName() : "Unknown Listener"),
                            e.getMessage(), e);
                    // Depending on policy, you might rethrow to fail bean creation if any listener is critical.
                    // For now, we log and continue, meaning other listeners might still be set up.
                    // If a listener failing to register is critical, uncomment the line below:
                    // throw new BeanCreationException("Critical error setting up event listener " + listener.getClass().getSimpleName(), e);
                }
            }
            log.info("All event listeners processed for registration.");
            return client;

        } catch (Exception e) {
            // This catches exceptions from DiscordClientBuilder...block() or critical listener setup failures if rethrown
            log.error("Failed to initialize Discord Gateway Client: {}", e.getMessage(), e);
            // Rethrow as a Spring-specific exception (or any RuntimeException)
            // This ensures Spring knows bean creation failed and will halt application startup.
            throw new BeanCreationException("Failed to create GatewayDiscordClient bean due to: " + e.getMessage(), e);
        }
        // Code here is unreachable if the catch block always rethrows.
    }

    @Bean
    public RestClient discordRestClient(GatewayDiscordClient client) {
        return client.getRestClient();
    }

}