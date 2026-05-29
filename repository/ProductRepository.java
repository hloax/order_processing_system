package com.orderprocessing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.orderprocessing.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
