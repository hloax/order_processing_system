package com.orderprocessing.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.orderprocessing.dto.order.*;
import com.orderprocessing.entity.*;
import com.orderprocessing.service.*;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/orders")
@Tag(
		name = "Orders",
		description = "Operations for creating and managing customer orders")
@SecurityRequirement(name = "Bearer Authentication")
public class OrderController {

	private final OrderService orderService;
	private final OrderAuditService auditService;

	public OrderController(OrderService orderService,
			OrderAuditService auditService) {
		
		this.orderService = orderService;
		this.auditService = auditService;
	}
	
	@Operation(
			summary = "Create a new order",
			description = "Create a customer order, reserves inventory and publishes an order created event"
	)
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Order created successfully"),
		@ApiResponse(responseCode = "400", description = "Invalid request or insufficient stock"),
		@ApiResponse(responseCode = "401", description = "Unauthorized"),
		@ApiResponse(responseCode = "404", description = "Product not found")
	})
	@PostMapping
	public ResponseEntity<OrderResponse> createOrder(
			@Valid @RequestBody OrderRequest request) {
		
		return ResponseEntity.ok(orderService.createOrder(request));
	}
	
	@Operation(
			summary = "Retrieve all orderd",
			description = "Returns every order in the system"
	)
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Orders returned successfully"),
		@ApiResponse(responseCode = "401", description = "Unauthorized")
	})
	@GetMapping
	public ResponseEntity<List<OrderResponse>> getMyOrders() {
		
		return ResponseEntity.ok(orderService.getMyOrders());
	}
	
	@Operation(
			summary = "Find order by ID",
			description = "Returns a single order."
	)
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Order found"),
		@ApiResponse(responseCode = "404", description = "Order not found")
	})
	@GetMapping("/{id}")
	public ResponseEntity<OrderResponse> getOrderById(
			@PathVariable Long id) {
		
		return ResponseEntity.ok(orderService.getOrderById(id));
	}
	
	@Operation(
			summary = "Update order status",
			description = "Changes the status of an order."
	)
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Status updated"),
		@ApiResponse(responseCode = "404", description = "Order not found"),
		@ApiResponse(responseCode = "403", description = "Admin role required")
	})
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
	
	@Operation(
			summary = "Check order's status history",
			description = "Lists history and timestamp of order status."
	)
	@GetMapping("/{orderId}/history")
	public ResponseEntity<List<OrderAudit>> getHistory(
			@PathVariable Long orderId) {
		
		return ResponseEntity.ok(
				auditService.getHistory(orderId));
	}
}
