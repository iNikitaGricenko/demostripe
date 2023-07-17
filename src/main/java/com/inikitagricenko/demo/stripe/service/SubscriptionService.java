package com.inikitagricenko.demo.stripe.service;

import com.inikitagricenko.demo.stripe.config.annotations.PerformanceMonitor;
import com.inikitagricenko.demo.stripe.model.Product;
import com.inikitagricenko.demo.stripe.model.Subscription;
import com.inikitagricenko.demo.stripe.persistence.SubscriptionPersistence;
import com.inikitagricenko.demo.stripe.service.interfaces.ICustomerService;
import com.inikitagricenko.demo.stripe.service.interfaces.IProductService;
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
	private final IProductService productService;
	private final ICustomerService customerService;

	@Override
	@PerformanceMonitor
	public Long add(Subscription subscription) {
		String customerReference = customerService.retrieve(subscription.getCustomer().getId()).getStripeReference();
		List<Product> products = productService.retrieveAll(subscription.getProductList().stream().map(Product::getId).toList());

		String subscriptionReference = stripeSubscriptionService.subscribeCustomer(customerReference, products).getId();
		subscription.setStripeReference(subscriptionReference);
		subscription.setCreated(LocalDateTime.now());
		return subscriptionPersistence.save(subscription);
	}

	@Override
	@PerformanceMonitor
	public void cancel(Long subscriptionId) {
		String reference = retrieve(subscriptionId).getStripeReference();
		stripeSubscriptionService.cancel(reference);
		subscriptionPersistence.delete(subscriptionId);
	}

	@Override
	@PerformanceMonitor
	public List<Subscription> retrieveAll() {
		return subscriptionPersistence.findAll();
	}

	@Override
	@PerformanceMonitor
	public Subscription retrieve(Long subscriptionId) {
		return subscriptionPersistence.findById(subscriptionId);
	}
}
