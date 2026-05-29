package com.orderprocessing.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	@Column(length = 1000)
	private String description;
	
	private BigDecimal price;
	
	private Integer stockQuantity;
	
	private String category;
	
	private LocalDateTime createdAt;
	
	@PrePersist
	public void prePersist() {
		createdAt = LocalDateTime.now();
	}
}
