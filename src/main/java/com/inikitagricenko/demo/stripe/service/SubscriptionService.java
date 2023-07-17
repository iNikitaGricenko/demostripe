package com.inikitagricenko.demo.stripe.service;

import com.inikitagricenko.demo.stripe.model.Subscription;
import com.inikitagricenko.demo.stripe.persistence.SubscriptionPersistence;
import com.inikitagricenko.demo.stripe.service.interfaces.ICustomerService;
import com.inikitagricenko.demo.stripe.service.interfaces.ISubscriptionService;
import com.inikitagricenko.demo.stripe.service.stripe.StripeSubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionService implements ISubscriptionService {

	private final StripeSubscriptionService stripeSubscriptionService;
	private final SubscriptionPersistence subscriptionPersistence;
	private final ICustomerService customerService;

	@Override
	public Long add(Subscription subscription) {
		String customerReference = customerService.retrieve(subscription.getCustomer().getId()).getStripeReference();
		String subscriptionReference = stripeSubscriptionService.subscribeCustomer(customerReference, subscription.getProductList()).getId();
		subscription.setStripeReference(subscriptionReference);
		subscription.setCreated(LocalDateTime.now());
		return subscriptionPersistence.save(subscription);
	}

	@Override
	public void cancel(Long subscriptionId) {
		String reference = retrieve(subscriptionId).getStripeReference();
		stripeSubscriptionService.cancel(reference);
		subscriptionPersistence.delete(subscriptionId);
	}

	@Override
	public List<Subscription> retrieveAll() {
		return subscriptionPersistence.findAll();
	}

	@Override
	public Subscription retrieve(Long subscriptionId) {
		return subscriptionPersistence.findById(subscriptionId);
	}
}
