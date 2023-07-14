package com.inikitagricenko.demo.stripe.service;

import com.inikitagricenko.demo.stripe.model.Subscription;
import com.inikitagricenko.demo.stripe.service.interfaces.ICustomerService;
import com.inikitagricenko.demo.stripe.service.interfaces.ISubscriptionService;
import com.inikitagricenko.demo.stripe.service.stripe.StripeSubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionService implements ISubscriptionService {

	private final StripeSubscriptionService stripeSubscriptionService;
	private final ICustomerService customerService;

	@Override
	public Long add(Subscription subscription) {
		String customerReference = customerService.retrieve(subscription.getCustomer().getId()).getStripeReference();
		String subscriptionReference = stripeSubscriptionService.subscribeCustomer(customerReference, subscription.getProductList()).getId();
		subscription.setStripeReference(subscriptionReference);

		return null; // TODO save in repository and return id
	}

	@Override
	public void cancel(Long subscriptionId) {

	}

	@Override
	public List<Subscription> retrieveAll() {
		return null;
	}

	@Override
	public Subscription retrieve(Long subscriptionId) {
		return null;
	}
}
