package com.orderprocessing.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.orderprocessing.dto.order.*;
import com.orderprocessing.entity.OrderStatus;
import com.orderprocessing.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	private final OrderService orderService;

	public OrderController(OrderService orderService) {
		
		this.orderService = orderService;
	}
	
	@PostMapping
	public ResponseEntity<OrderResponse> createOrder(
			@RequestBody OrderRequest request) {
		
		return ResponseEntity.ok(orderService.createOrder(request, null));
	}
	
	@GetMapping
	public ResponseEntity<List<OrderResponse>> getMyOrders() {
		
		return ResponseEntity.ok(orderService.getMyOrders());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<OrderResponse> getOrderById(
			@PathVariable Long id) {
		
		return ResponseEntity.ok(orderService.getOrderById(id));
	}
	
	@PatchMapping("/{id}/status")
	public ResponseEntity<OrderResponse> updateStatus(
			@PathVariable Long id,
			@RequestBody UpdateOrderStatusRequest request) {
		
		return ResponseEntity.ok(
				orderService.updateOrderStatus(
						id, request.getStatus()));
	}
	
	@GetMapping("/paged")
	public ResponseEntity<Page<OrderResponse>> getOrders(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(required = false) OrderStatus status) {
		
		return ResponseEntity.ok(
					orderService.getOrders(page, size, status));
	}
}
