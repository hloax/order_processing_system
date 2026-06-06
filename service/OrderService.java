package com.orderprocessing.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.orderprocessing.dto.order.*;
import com.orderprocessing.entity.OrderStatus;

public interface OrderService {

	OrderResponse createOrder(OrderRequest request);
	List<OrderResponse> getMyOrders();
	OrderResponse getOrderById(Long id);
	OrderResponse updateOrderStatus(Long orderId, OrderStatus status);
	Page<OrderResponse> getOrders(int page, int size, OrderStatus status);
}
