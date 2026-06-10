package com.orderprocessing.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.orderprocessing.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long>{
	Optional<UserEntity> findByEmail(String email);

}
