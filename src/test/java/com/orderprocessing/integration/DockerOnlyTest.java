package com.orderprocessing.integration;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;

public class DockerOnlyTest {

	@Test
	void dockerShouldStart() {
				
		PostgreSQLContainer<?> postgres =
				new PostgreSQLContainer<>("postgres:16");
		
		postgres.start();
		System.out.println("Printout: " + postgres.getJdbcUrl());
		postgres.stop();
		System.out.println("Stopped");
	}
}
