package com.orderprocessing.repository;

import java.util.List;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import com.orderprocessing.entity.*;

public interface OrderRepository extends JpaRepository<Order, Long> {
	List<Order> findByUser(UserEntity user);
	List<Order> findByStatus(OrderStatus status);
	Page<Order> findByUser(UserEntity user, Pageable pageable);
	Page<Order> findByUserAndStatus(
			UserEntity user, OrderStatus status, Pageable pageable);
}
