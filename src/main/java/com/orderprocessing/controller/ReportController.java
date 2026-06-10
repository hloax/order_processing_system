package com.orderprocessing.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.orderprocessing.dto.report.*;
import com.orderprocessing.service.ReportService;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

	private final ReportService reportService;

	public ReportController(ReportService reportService) {
		this.reportService = reportService;
	}
	
	@GetMapping("/revenue")
	public ResponseEntity<RevenueResponse> getRevenue() {
		return ResponseEntity.ok(reportService.getRevenue());
	}
	
	@GetMapping("/total-orders")
	public ResponseEntity<Long> getTotalOrders() {
		return ResponseEntity.ok(reportService.getTotalOrders());
	}
	
	@GetMapping("/order-summary")
	public ResponseEntity<List<OrderSummaryResponse>> getOrderSummary() {
		return ResponseEntity.ok(reportService.getOrderSummary());
	}
}
