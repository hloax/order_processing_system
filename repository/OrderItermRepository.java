package com.orderprocessing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.orderprocessing.entity.OrderItem;

public interface OrderItermRepository extends JpaRepository<OrderItem, Long> {

}
