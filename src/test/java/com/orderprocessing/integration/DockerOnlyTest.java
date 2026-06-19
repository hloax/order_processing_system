package com.orderprocessing.integration;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;

public class DockerOnlyTest {

	@Test
	void dockerShouldStart() {
		
		System.out.println("Got inside docker function");
		
		PostgreSQLContainer<?> postgres =
				new PostgreSQLContainer<>("postgres:16");
		
		System.out.println("Created");
		
		postgres.start();
		System.out.println("Printout: " + postgres.getJdbcUrl());
		postgres.stop();
	}
}
