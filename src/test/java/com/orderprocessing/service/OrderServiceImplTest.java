package com.orderprocessing.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;

import com.orderprocessing.dto.order.*;
import com.orderprocessing.entity.*;
import com.orderprocessing.event.OrderCreatedEvent;
import com.orderprocessing.exception.*;
import com.orderprocessing.messaging.OrderEventPublisher;
import com.orderprocessing.repository.*;
import com.orderprocessing.util.SecurityUtils;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

	@Mock
	private OrderRepository orderRepository;
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private ProductRepository productRepository;
	
	@Mock
	private OrderEventPublisher orderEventPublisher;
	
	@InjectMocks
	private OrderServiceImpl orderService;
	
	
	@Test
	void shouldCreateOrderSuccessfully() {
		
		try (MockedStatic<SecurityUtils> mocked = Mockito.mockStatic(SecurityUtils.class)) {
			
			mocked.when(SecurityUtils::getCurrentUserEmail)
					.thenReturn("test@test.com");	
			
			//Arrange
			UserEntity user = new UserEntity();
			user.setId(1L);
			user.setEmail("test@test.com");
			
			Product product = new Product();
			product.setId(1L);
			product.setName("Laptop");
			product.setPrice(BigDecimal.valueOf(10000));
			product.setStockQuantity(10);
			
			OrderItemRequest itemRequest = new OrderItemRequest();
			itemRequest.setProductId(1L);
			itemRequest.setQuantity(2);
			
			OrderRequest orderRequest = new OrderRequest();
			orderRequest.setItems(List.of(itemRequest));
			
			when(userRepository.findByEmail("test@test.com"))
					.thenReturn(Optional.of(user));
			
			when(productRepository.findById(1L))
					.thenReturn(Optional.of(product));
			
			when(productRepository.save(any(Product.class)))
					.thenAnswer(invocation -> invocation.getArgument(0));
			
			when(orderRepository.save(any(Order.class)))
					.thenAnswer(invocation -> {
						Order saved = invocation.getArgument(0);
						saved.setId(1L);
						return saved;
					});
			
			//Act
			OrderResponse response =
					orderService.createOrder(orderRequest);
			
			//Assert
			assertNotNull(response);
			
			assertEquals(
					BigDecimal.valueOf(20000), response.getTotalAmount());
			
			assertEquals(8, product.getStockQuantity());
			
			verify(orderRepository, 
					times(1))
					.save(any(Order.class));
			
			verify(orderEventPublisher,
					times(1))
					.publishOrderCreated(any(OrderCreatedEvent.class));
		}
	}
	
	@Test
	void shouldThrowExceptionWhenStockIsInsufficient() {
		
		try (MockedStatic<SecurityUtils> mocked = Mockito.mockStatic(SecurityUtils.class)) {
			
			mocked.when(SecurityUtils::getCurrentUserEmail)
					.thenReturn("test@test.com");	
			
			Product product = new Product();
			product.setId(1L);
			product.setStockQuantity(1);
			product.setPrice(BigDecimal.valueOf(1000));
			
			when(userRepository.findByEmail(anyString()))
					.thenReturn(Optional.of(new UserEntity()));
			
			when(productRepository.findById(1L))
					.thenReturn(Optional.of(product));
			
			OrderItemRequest itemRequest = new OrderItemRequest();
			itemRequest.setProductId(1L);
			itemRequest.setQuantity(50);
			
			OrderRequest request = new OrderRequest();
			request.setItems(List.of(itemRequest));
			
			assertThrows(InsufficientStockException.class,
					() -> orderService.createOrder(
							request)
			);
		}
	}
	
	@Test
	void shouldThrowExceptionWhenProductNotFound() {
		
		try (MockedStatic<SecurityUtils> mocked = Mockito.mockStatic(SecurityUtils.class)) {
			
			mocked.when(SecurityUtils::getCurrentUserEmail)
					.thenReturn("test@test.com");

			when(userRepository.findByEmail(anyString()))
					.thenReturn(Optional.of(new UserEntity()));
			
			when(productRepository.findById(1L))
					.thenReturn(Optional.empty());
			
			OrderItemRequest item = new OrderItemRequest();
			item.setProductId(1L);
			item.setQuantity(2);
			
			OrderRequest request = new OrderRequest();
			request.setItems(List.of(item));
			
			assertThrows(ProductNotFoundException.class,
					() -> orderService.createOrder(
							request)
			);
		}
		
	}
	
	@Test
	void shouldThrowExceptionWhenUserNotFound() {
		
		try (MockedStatic<SecurityUtils> mocked = Mockito.mockStatic(SecurityUtils.class)) {
			
			mocked.when(SecurityUtils::getCurrentUserEmail)
					.thenReturn("test@test.com");
		
			when(userRepository.findByEmail(anyString()))
					.thenReturn(Optional.empty());
			
			OrderItemRequest item = new OrderItemRequest();
			item.setProductId(1L);
			item.setQuantity(2);
			
			OrderRequest request = new OrderRequest();
			request.setItems(List.of(item));
			
			assertThrows(RuntimeException.class,
					() -> orderService.createOrder(
							request)
			);
		}
	}
}
