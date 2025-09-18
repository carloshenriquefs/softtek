package com.saudemental.api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = SaudeMentalApiApplication.class)
@ActiveProfiles("test")
class SaudeMentalApiApplicationTests {

	@Test
	void contextLoads() {
		// Test passes if the application context loads successfully
	}

}
