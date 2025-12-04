package com.botofholding.bot.Config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class WebClientConfig {

    /**
     * Configures a robust, production-ready ConnectionProvider for the WebClient.
     * This provider is designed to prevent idle connection timeout errors.
     *
     * @return A configured ConnectionProvider.
     */
    @Bean
    public ConnectionProvider connectionProvider() {
        return ConnectionProvider.builder("custom-connection-pool")
                // The maximum number of connections in the pool.
                .maxConnections(50)
                // The maximum number of requests that can be queued if all connections are busy.
                .pendingAcquireMaxCount(-1)
                // A timeout for acquiring a connection from the pool.
                .pendingAcquireTimeout(Duration.ofSeconds(45))
                // The maximum time a connection can be idle before it is closed and evicted.
                // This should be less than the server's or any intermediary's idle timeout.
                .maxIdleTime(Duration.ofMinutes(10))
                // The maximum time a connection can live, regardless of activity.
                .maxLifeTime(Duration.ofMinutes(60))
                .build();
    }

    /**
     * Configures the underlying HttpClient for the WebClient.
     * This client enables TCP Keep-Alive and sets connection/read/write timeouts.
     *
     * @param connectionProvider The custom ConnectionProvider to use.
     * @return A configured HttpClient.
     */
    @Bean
    public HttpClient httpClient(ConnectionProvider connectionProvider) {
        return HttpClient.create(connectionProvider)
                // Set a timeout for establishing the initial connection.
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000) // 10 seconds
                // Enable TCP Keep-Alive to help maintain long-lived connections through firewalls.
                .option(ChannelOption.SO_KEEPALIVE, true)
                .responseTimeout(Duration.ofSeconds(60)) // 60-second timeout for receiving a response
                .doOnConnected(conn -> conn
                        // Set a read timeout for each individual read operation.
                        .addHandlerLast(new ReadTimeoutHandler(60, TimeUnit.SECONDS))
                        // Set a write timeout for each individual write operation.
                        .addHandlerLast(new WriteTimeoutHandler(60, TimeUnit.SECONDS)));
    }

    /**
     * Provides the primary WebClient.Builder bean for the application.
     * By marking this as @Primary, we ensure that any component that autowires a
     * WebClient.Builder (like our ApiClientImpl) will receive this fully configured
     * instance.
     *
     * @param httpClient The custom HttpClient to use.
     * @return A pre-configured WebClient.Builder.
     */
    @Bean
    @Primary
    public WebClient.Builder webClientBuilder(HttpClient httpClient) {
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient));
    }
}
