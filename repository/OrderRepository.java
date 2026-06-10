package com.orderprocessing.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;

import com.orderprocessing.entity.*;

public interface OrderRepository extends JpaRepository<Order, Long> {
	List<Order> findByUser(UserEntity user);
	List<Order> findByStatus(OrderStatus status);
	Page<Order> findByUser(UserEntity user, Pageable pageable);
	Page<Order> findByUserAndStatus(
			UserEntity user, OrderStatus status, Pageable pageable);
	
	@Query("""
			SELECT COALESCE(
				SUM(o.totalAmount),
				0)
			FROM Order o
			WHERE o.status = 'COMPLETED'
			""")
	BigDecimal getTotalRevenue();
	
	@Query("""
			SELECT COUNT(o)
			FROM Order o
			""")
	Long getTotalOrders();
	
	@Query("""
			SELECT o.status,
				COUNT(o)
			FROM Order o
			GROUP BY o.status
			""")
	List<Object[]> getOrderSummary();
	
}
