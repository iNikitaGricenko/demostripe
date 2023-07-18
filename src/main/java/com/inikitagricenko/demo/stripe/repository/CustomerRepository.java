package com.inikitagricenko.demo.stripe.repository;

import com.inikitagricenko.demo.stripe.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
	Optional<CustomerEntity> findByEmail(String email);
}
