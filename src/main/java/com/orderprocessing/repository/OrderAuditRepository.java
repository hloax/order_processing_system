package com.orderprocessing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.orderprocessing.entity.OrderAudit;

public interface OrderAuditRepository extends JpaRepository<OrderAudit, Long> {

	List<OrderAudit> findByOrderIdOrderByCreatedAtAsc(Long orderId);
}
