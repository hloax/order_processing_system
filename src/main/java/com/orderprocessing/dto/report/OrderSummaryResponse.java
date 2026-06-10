package com.orderprocessing.dto.report;

public class OrderSummaryResponse {

	private String status;
	
	private Long count;

	public OrderSummaryResponse(String status, Long count) {
		
		this.status = status;
		this.count = count;
	}

	public String getStatus() {
		return status;
	}

	public Long getCount() {
		return count;
	}
	
}
