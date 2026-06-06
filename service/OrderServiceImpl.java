package com.orderprocessing.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import com.orderprocessing.dto.order.*;
import com.orderprocessing.entity.*;
import com.orderprocessing.exception.*;
import com.orderprocessing.repository.*;
import com.orderprocessing.util.SecurityUtils;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;
	private final ProductRepository productRepository;
	private final UserRepository userRepository;
	
	public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository,
			UserRepository userRepository) {
		
		this.orderRepository = orderRepository;
		this.productRepository = productRepository;
		this.userRepository = userRepository;
	}
	
	@Override
	public OrderResponse createOrder(OrderRequest request) {
		
		String email = SecurityUtils.getCurrentUserEmail();
		
		UserEntity user =
				userRepository.findByEmail(email)
				.orElseThrow(() ->
						new RuntimeException(
								"User not found"));
		
		Order order = new Order();
		
		order.setUser(user);
		order.setStatus(OrderStatus.CREATED);
		
		List<OrderItem> orderItems = new ArrayList<>();
		
		BigDecimal totalAmount = BigDecimal.ZERO;
		
		for (OrderItemRequest itemRequest : request.getItems()) {
			
			Product product =
					productRepository.findById(
							itemRequest.getProductId())
					.orElseThrow(() ->
							new ProductNotFoundException(
									itemRequest.getProductId()));
			
			if (product.getStockQuantity() < itemRequest.getQuantity()) {
				
				throw new InsufficientStockException(product.getName());
			}
			
			product.setStockQuantity(
					product.getStockQuantity() - itemRequest.getQuantity());
			
			productRepository.save(product);
			
			OrderItem orderItem = new OrderItem();
			
			orderItem.setProduct(product);
			orderItem.setQuantity(itemRequest.getQuantity());
			orderItem.setPrice(product.getPrice());
			orderItems.add(orderItem);
			
			BigDecimal itemTotal =
					product.getPrice()
							.multiply(
									BigDecimal.valueOf(
											itemRequest.getQuantity()));
			
			totalAmount = totalAmount.add(itemTotal);
		}
		
		order.setTotalAmount(totalAmount);
		order.setOrderItems(orderItems);
		
		for (OrderItem item : orderItems) {
			item.setOrder(order);
		}
		
		Order savedOrder = orderRepository.save(order);
		
		return mapToResponse(savedOrder);
		
	}
	
	@Override
	public List<OrderResponse> getMyOrders() {
		
		String email = SecurityUtils.getCurrentUserEmail();
		
		UserEntity user =
				userRepository.findByEmail(email)
				.orElseThrow(() ->
						new RuntimeException(
								"User not found"));
		
		return orderRepository.findByUser(user)
				.stream()
				.map(this::mapToResponse)
				.toList();
	}
	
	@Override
	public OrderResponse getOrderById(Long id) {
		
		String email = SecurityUtils.getCurrentUserEmail();
		
		UserEntity currentUser =
				userRepository.findByEmail(email)
				.orElseThrow(() ->
						new RuntimeException(
								"User not found"));
		
		Order order =
				orderRepository.findById(id)
				.orElseThrow(() ->
						new OrderNotFoundException(id));
		
		boolean owner =
				order.getUser()
					.getId()
					.equals(currentUser.getId());
		
		if (!owner && !isAdmin(currentUser)) {
			throw new UnauthorizedOrderAccessException();
		}
		
		return mapToResponse(order);
	}
	
	@Override
	public OrderResponse updateOrderStatus(Long orderId, OrderStatus status) {
		
		Order order =
				orderRepository.findById(orderId)
				.orElseThrow(() ->
						new OrderNotFoundException(orderId));
		
		if (!isValidTransition(order.getStatus(), status)) {
			
			throw new InvalidOrderStatusException(
					"Invalid transition from "
					+ order.getStatus()
					+ " to "
					+ status);
		}
		
		order.setStatus(status);
		
		Order updated = orderRepository.save(order);
		
		return mapToResponse(updated);
	}
	
	@Override
	public Page<OrderResponse> getOrders(int page, int size, OrderStatus status) {
		
		String email = SecurityUtils.getCurrentUserEmail();
		
		UserEntity user =
				userRepository.findByEmail(email)
				.orElseThrow(() ->
						new RuntimeException(
								"User not found"));
		
		Pageable pageable = PageRequest.of(page, size);
		
		Page<Order> orders;
		
		if (status == null) {
			
			orders = orderRepository.findByUser(user, pageable);
			
		} else {
			
			orders = orderRepository.findByUserAndStatus(
						user, status, pageable);
		}
		
		return orders.map(this::mapToResponse);
	}
		
	private OrderResponse mapToResponse(Order order) {
		
		List<OrderItemResponse> items =
				order.getOrderItems()
					.stream()
					.map(item ->
							new OrderItemResponse(
									item.getProduct().getId(),
									item.getProduct().getName(),
									item.getQuantity(),
									item.getPrice()
							))
					.toList();
		
		return new OrderResponse(
				order.getId(),
				order.getStatus().name(),
				order.getTotalAmount(),
				items
		);
	}
	
	private boolean isValidTransition(OrderStatus current, OrderStatus next) {
				
		return switch (current) {
		
			case CREATED ->
					next == OrderStatus.PROCESSING || next == OrderStatus.CANCELLED;
					
			case PROCESSING ->
					next == OrderStatus.SHIPPED;
					
			case SHIPPED ->
					next == OrderStatus.COMPLETED;
					
			case COMPLETED,
					CANCELLED -> false;
		};
	}
	
	private boolean isAdmin(UserEntity user) {
		return user.getRole() == Role.ADMIN;
	}
}
