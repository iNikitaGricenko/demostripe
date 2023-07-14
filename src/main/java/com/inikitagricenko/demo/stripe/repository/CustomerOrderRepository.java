package com.inikitagricenko.demo.stripe.repository;

import com.inikitagricenko.demo.stripe.entity.CustomerOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrderEntity, Long> {
}
