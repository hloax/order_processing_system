package com.orderprocessing.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.*;
import io.swagger.v3.oas.models.security.*;

@Configuration
public class OpenApiConfig {

	@Bean
	OpenAPI orderProcessingAPI() {
		
		final String securitySchemeName = "Bearer Authentication";
		
		return new OpenAPI()
				
				.addSecurityItem(
						new SecurityRequirement()
								.addList(securitySchemeName))
				
				.components(
						new Components()
								.addSecuritySchemes(
										securitySchemeName,
										
										new SecurityScheme()
												.name(securitySchemeName)
												.type(SecurityScheme.Type.HTTP)
												.scheme("bearer")
												.bearerFormat("JWT")
										))
				
				.info(new Info()
						.title("Order Processing API")
						.version("1.0")
						.description("Order Processing System built with Spring Boot 4,"
								+ "RabbitMQ, PostgreSQL and JWT Security.")
						);
		
	}
}
