package com.orderprocessing.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.orderprocessing.entity.Order;
import com.orderprocessing.entity.UserEntity;

public interface OrderRepository extends JpaRepository<Order, Long> {
	List<Order> findByUser(UserEntity user);
}
