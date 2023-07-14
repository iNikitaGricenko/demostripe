package com.inikitagricenko.demo.stripe.repository;

import com.inikitagricenko.demo.stripe.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}