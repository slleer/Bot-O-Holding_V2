package com.botofholding.bot;


import com.botofholding.bot.Service.ApiClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.rest.RestClient;
import discord4j.rest.service.ApplicationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ActiveProfiles("test") // [FIX] Activate the "test" profile.
@SpringBootTest
class BotApplicationTests {

	// [FIX] Use a static nested @TestConfiguration to provide mocks.
	// This is the modern, recommended replacement for the deprecated @MockBean.
	// It's more explicit and avoids the "magic" of bean replacement.
	@TestConfiguration
	static class TestConfig {
		@Bean
		@Primary // Ensure this mock bean is used instead of the real one
		public ApiClient apiClient() {
			return Mockito.mock(ApiClient.class);
		}

		/**
		 * [FIX] Provide a mock GatewayDiscordClient to prevent the application
		 * from trying to make a real connection to Discord during tests.
		 * This is critical because the test profile uses a dummy token.
		 */
		@Bean
		@Primary
		public GatewayDiscordClient gatewayDiscordClient() {
			GatewayDiscordClient mockGateway = Mockito.mock(GatewayDiscordClient.class);

			// The GlobalCommandRegistrar depends on RestClient, which is derived from the Gateway.
			// We need to provide a mock RestClient with stubbed methods to prevent NPEs during startup.
			RestClient mockRestClient = Mockito.mock(RestClient.class);
			ApplicationService mockApplicationService = Mockito.mock(ApplicationService.class);

			// Stub the methods that GlobalCommandRegistrar calls on RestClient
			Mockito.when(mockRestClient.getApplicationId()).thenReturn(Mono.just(123456789L));
			Mockito.when(mockRestClient.getApplicationService()).thenReturn(mockApplicationService);

			// Stub the method on ApplicationService that the registrar calls.
			// Return an empty Flux to simulate a successful, no-op command registration.
			Mockito.when(mockApplicationService.bulkOverwriteGlobalApplicationCommand(Mockito.anyLong(), Mockito.any()))
					.thenReturn(Flux.empty());

			// Wire the fully configured mock RestClient to be returned by the mock Gateway.
			Mockito.when(mockGateway.getRestClient()).thenReturn(mockRestClient);
			return mockGateway;
		}
	}

	@Test
	void contextLoads() {
	}

}
