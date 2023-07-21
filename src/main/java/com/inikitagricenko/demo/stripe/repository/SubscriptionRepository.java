package com.inikitagricenko.demo.stripe.repository;

import com.inikitagricenko.demo.stripe.entity.SubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Long> {

	@Modifying
	@Query("UPDATE subscription e SET e.deleted = false, e.deleteAt = null WHERE e.id = ?1")
	void undelete(Long subscriptionId);
}