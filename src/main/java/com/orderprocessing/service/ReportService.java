package com.orderprocessing.service;

import java.util.List;
import com.orderprocessing.dto.report.*;

public interface ReportService {

	RevenueResponse getRevenue();
	Long getTotalOrders();
	List<OrderSummaryResponse> getOrderSummary();
}
