package com.botofholding.bot;


import com.botofholding.bot.Service.ApiClient;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ActiveProfiles;

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
	}

	@Test
	void contextLoads() {
	}

}
