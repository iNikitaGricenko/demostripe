package com.inikitagricenko.demo.stripe.repository;

import com.inikitagricenko.demo.stripe.entity.SubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Long> {
}