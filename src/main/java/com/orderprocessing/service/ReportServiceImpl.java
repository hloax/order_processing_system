package com.orderprocessing.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.orderprocessing.dto.report.*;
import com.orderprocessing.repository.OrderRepository;

@Service
public class ReportServiceImpl implements ReportService {

	private final OrderRepository orderRepository;

	public ReportServiceImpl(OrderRepository orderRepository) {
		
		this.orderRepository = orderRepository;
	}
	
	@Override
	public RevenueResponse getRevenue() {
		return new RevenueResponse(orderRepository.getTotalRevenue());
	}
	
	@Override
	public Long getTotalOrders() {
		return orderRepository.getTotalOrders();
	}
	
	@Override
	public List<OrderSummaryResponse> getOrderSummary() {
		
		return orderRepository
				.getOrderSummary()
				.stream()
				.map(result ->
						new OrderSummaryResponse(
								result[0].toString(),
								(Long) result[1]))
				.toList();
	}
}
