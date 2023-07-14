package com.inikitagricenko.demo.stripe.repository;

import com.inikitagricenko.demo.stripe.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
}
