package com.inikitagricenko.demo.stripe.service;

import com.inikitagricenko.demo.stripe.config.annotations.ProductValidation;
import com.inikitagricenko.demo.stripe.config.annotations.PerformanceMonitor;
import com.inikitagricenko.demo.stripe.model.Customer;
import com.inikitagricenko.demo.stripe.model.Subscription;
import com.inikitagricenko.demo.stripe.persistence.SubscriptionPersistence;
import com.inikitagricenko.demo.stripe.service.interfaces.ICustomerService;
import com.inikitagricenko.demo.stripe.service.interfaces.IProductService;
import com.inikitagricenko.demo.stripe.service.interfaces.ISubscriptionService;
import com.inikitagricenko.demo.stripe.service.stripe.StripeSubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class SubscriptionService implements ISubscriptionService {

	private final StripeSubscriptionService stripeSubscriptionService;
	private final SubscriptionPersistence subscriptionPersistence;
	private final IProductService productService;
	private final ICustomerService customerService;

	@Override
	@PerformanceMonitor
	@ProductValidation
	public Long add(Subscription subscription) {
		subscription.setCreated(LocalDateTime.now());
		return subscriptionPersistence.save(subscription);
	}

	@Override
	@PerformanceMonitor
	public long subscribeCustomer(long subscriptionId, long customerId) {
		Subscription subscription = retrieve(subscriptionId);
		Customer customer = customerService.retrieve(customerId);

		String customerReference = customer.getStripeReference();
		String subscriptionReference = stripeSubscriptionService.subscribeCustomer(customerReference, subscription.getDiscount(), subscription.getProductList()).getId();

		subscription.setStripeReference(subscriptionReference);
		Optional.ofNullable(subscription.getCustomerList())
				.filter(Predicate.not(Set::isEmpty))
				.ifPresentOrElse(
						list -> list.add(customer),
						() -> subscription.setCustomerList(Set.of(customer)));

		return subscriptionPersistence.update(subscriptionId, subscription);
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
	public void resume(Long subscriptionId) {
		String reference = retrieve(subscriptionId).getStripeReference();
		stripeSubscriptionService.resume(reference);
		subscriptionPersistence.undelete(subscriptionId);
	}

	@Override
	@PerformanceMonitor
	public Page<Subscription> retrieveAll(Pageable pageable) {
		return subscriptionPersistence.findAll(pageable);
	}

	@Override
	@PerformanceMonitor
	public Subscription retrieve(Long subscriptionId) {
		return subscriptionPersistence.findById(subscriptionId);
	}
}
