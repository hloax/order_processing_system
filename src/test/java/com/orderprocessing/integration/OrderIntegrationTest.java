package com.orderprocessing.integration;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;

import com.orderprocessing.entity.*;
import com.orderprocessing.repository.*;

public class OrderIntegrationTest extends BaseIntegrationTest{

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Test
	@WithMockUser(username = "test@test.com", authorities = {"USER"})
	void shouldCreateOrderEndToEnd() throws Exception {
		
		UserEntity user = new UserEntity();
		user.setEmail("test@test.com");
		userRepository.save(user);
		
		Product product = new Product();
		product.setName("Laptop");
		product.setPrice(BigDecimal.valueOf(10000));
		product.setStockQuantity(10);
		productRepository.save(product);
		
		String requestJson = """
				{
					"items" : [
						{
							"productId" : %d,
							"quantity" : 2
						}
					]
				}
				""".formatted(product.getId());
		
		mockMvc.perform(post("/api/orders")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestJson))
				.andExpect(status().isOk());
	}
	
}
