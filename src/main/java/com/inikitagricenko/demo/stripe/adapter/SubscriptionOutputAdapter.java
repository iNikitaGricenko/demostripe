package com.inikitagricenko.demo.stripe.adapter;

import com.inikitagricenko.demo.stripe.model.Subscription;

import java.util.List;

public interface SubscriptionOutputAdapter {

	List<Subscription> retrieveAll();

	Subscription retrieve(Long subscriptionId);
}
