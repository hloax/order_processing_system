package com.orderprocessing.dto.report;

import java.math.BigDecimal;

public class RevenueResponse {

	private BigDecimal totalRevenue;

	public RevenueResponse(BigDecimal totalRevenue) {
		this.totalRevenue = totalRevenue;
	}

	public BigDecimal getTotalRevenue() {
		return totalRevenue;
	}

	public void setTotalRevenue(BigDecimal totalRevenue) {
		this.totalRevenue = totalRevenue;
	}
		
}
