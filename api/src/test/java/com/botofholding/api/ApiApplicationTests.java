package com.botofholding.api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test") // Explicitly activate the 'test' profile for clarity and consistency.
@AutoConfigureTestDatabase
class ApiApplicationTests {

	@Test
	void contextLoads() {
	}

}
