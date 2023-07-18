package com.inikitagricenko.demo.stripe.repository;

import com.inikitagricenko.demo.stripe.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

	@Modifying
	@Query("UPDATE product e SET e.quantity = e.quantity - ?2 WHERE e.id = ?1")
	void reduceQuantity(long id, long quantity);

	@Modifying
	@Query("UPDATE product e SET e.quantity = e.quantity + ?2 WHERE e.id = ?1")
	void increaseQuantity(long id, long quantity);

	boolean existsByIdIn(List<Long> ids);
}