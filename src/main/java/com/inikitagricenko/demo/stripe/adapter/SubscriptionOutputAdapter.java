package com.inikitagricenko.demo.stripe.adapter;

import com.inikitagricenko.demo.stripe.model.Subscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SubscriptionOutputAdapter {

	Page<Subscription> retrieveAll(Pageable pageable);

	Subscription retrieve(Long subscriptionId);
}
