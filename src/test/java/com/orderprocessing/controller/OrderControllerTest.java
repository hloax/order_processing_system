package com.orderprocessing.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.orderprocessing.dto.order.*;
import com.orderprocessing.exception.*;
import com.orderprocessing.security.*;
import com.orderprocessing.service.*;

@WebMvcTest(controllers = OrderController.class)
public class OrderControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockitoBean
	private OrderService orderService;
	
	@MockitoBean
	private OrderAuditService orderAuditService;
	
	@MockitoBean
	private JwtService jwtService;
	
	@MockitoBean
	private CustomUserDetailsService customUserDetailsService;
	
	
	@Test
	@WithMockUser(username= "test@test.com", authorities= {"USER"})
	void shouldCreateOrderViaApi() throws Exception {
		
		OrderItemResponse itemResponse = new OrderItemResponse();
		itemResponse.setProductId(1L);
		itemResponse.setQuantity(2);
		itemResponse.setPrice(BigDecimal.valueOf(20000));
		
		OrderResponse response =
				new OrderResponse(
						1L,
						"CREATED",
						BigDecimal.valueOf(20000),
						List.of(itemResponse)
				);
		response.setTotalAmount(BigDecimal.valueOf(20000));
		
		when(orderService.createOrder(any()))
				.thenReturn(response);
		
		String requestJson = """
				{
					"items": [
						{
							"productId": 1,
							"quantity": 2
						}
					]
				}
				""";
		
		mockMvc.perform(post("/api/orders")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestJson))
				.andExpect(status().isOk());
		
	}
	
	@Test
	@WithMockUser(username= "test@test.com", authorities= {"USER"})
	void shouldReturnBadRequestWhenQuantityIsInvalid() throws Exception {
		
		String requestJson = """
				{
					"items" : [
						{
							"productId" : 1,
							"quantity" : 0
						}
					]
				}
				""";
		
		mockMvc.perform(post("/api/orders")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestJson))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	@WithMockUser(username= "test@test.com", authorities= {"USER"})
	void shouldReturnBadRequestWhenItemsAreEmpty() throws Exception {
		
		String requestJson = """
				{
					"items" : []
				}
				""";
		
		mockMvc.perform(post("/api/orders")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestJson))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	@WithMockUser(username= "test@test.com", authorities= {"USER"})
	void shouldReturnNotFoundWhenProductDoesNotExist() throws Exception {
		
		when(orderService.createOrder(any()))
				.thenThrow(new ProductNotFoundException(999L));
		
		String requestJson = """
				{
					"items" : [
						{
							"productId" : 99,
							"quantity" : 1
						}
					]
				}
				""";
		
		mockMvc.perform(post("/api/orders")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestJson))
				.andExpect(status().isNotFound());
	}
	
	@Test
	@WithMockUser(username= "test@test.com", authorities= {"USER"})
	void shouldReturnConflictWhenStockIsInsufficient() throws Exception {
		
		when(orderService.createOrder(any()))
				.thenThrow(new InsufficientStockException("Laptop"));
		
		String requestJson = """
				{
					"items" : [
						{
							"productId" : 1,
							"quantity" : 100
						}
					]
				}
				""";
		
		mockMvc.perform(post("/api/orders")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestJson))
				.andExpect(status().isConflict());
	}
}
