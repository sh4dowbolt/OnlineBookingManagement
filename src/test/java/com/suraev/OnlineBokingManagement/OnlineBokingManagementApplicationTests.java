package com.suraev.OnlineBokingManagement;

import com.suraev.OnlineBokingManagement.config.TestConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;



@SpringBootTest(classes = {OnlineBokingManagementApplicationTests.class})
//@ActiveProfiles("test")

class OnlineBokingManagementApplicationTests extends TestConfig {

	@Test
	@DisplayName("Testcontainers is running")
	void contextLoads() {
		System.out.println("Testcontainers is running");
	}

}
