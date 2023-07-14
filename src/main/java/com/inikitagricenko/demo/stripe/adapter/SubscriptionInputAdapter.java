package com.inikitagricenko.demo.stripe.adapter;

import com.inikitagricenko.demo.stripe.model.Subscription;

public interface SubscriptionInputAdapter {

	Long add(Subscription subscription);

	void cancel(Long subscriptionId);

}
