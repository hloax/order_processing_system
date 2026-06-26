package com.orderprocessing.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.orderprocessing.entity.OrderAudit;
import com.orderprocessing.repository.OrderAuditRepository;

@Service
public class OrderAuditService {

	private final OrderAuditRepository auditRepository;

	public OrderAuditService(OrderAuditRepository auditRepository) {
		this.auditRepository = auditRepository;
	}
	
	public void recordStatus(Long orderId, String status) {
		
		OrderAudit audit = new OrderAudit();
		
		audit.setOrderId(orderId);
		audit.setStatus(status);
		audit.setCreatedAt(LocalDateTime.now());
		
		auditRepository.save(audit);
	}
	
	public List<OrderAudit> getHistory(Long orderId) {
		return auditRepository.findByOrderIdOrderByCreatedAtAsc(orderId);
	}
}
