package com.inikitagricenko.demo.stripe.controller;

import com.inikitagricenko.demo.stripe.adapter.SubscriptionInputAdapter;
import com.inikitagricenko.demo.stripe.adapter.SubscriptionOutputAdapter;
import com.inikitagricenko.demo.stripe.controller.interfaces.SubscriptionEndpoint;
import com.inikitagricenko.demo.stripe.mapper.SubscriptionMapper;
import com.inikitagricenko.demo.stripe.model.Subscription;
import com.inikitagricenko.demo.stripe.model.dto.SubscriptionCreateDTO;
import com.inikitagricenko.demo.stripe.model.dto.SubscriptionResponseDTO;
import com.inikitagricenko.demo.stripe.wrapper.SubscriptionPage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscription")
@RequiredArgsConstructor
public class SubscriptionController implements SubscriptionEndpoint {

	private final SubscriptionInputAdapter subscriptionInputAdapter;
	private final SubscriptionOutputAdapter subscriptionOutputAdapter;
	private final SubscriptionMapper subscriptionMapper;

	@Override
	@GetMapping
	public SubscriptionPage retrieveSubscriptions(Pageable pageable) {
		Page<Subscription> subscriptions = subscriptionOutputAdapter.retrieveAll(pageable);
		return new SubscriptionPage(subscriptions.map(subscriptionMapper::toResponse));
	}

	@Override
	@GetMapping("/{subscriptionId}")
	public SubscriptionResponseDTO retrieveSubscription(@PathVariable Long subscriptionId) {
		Subscription subscription = subscriptionOutputAdapter.retrieve(subscriptionId);
		return subscriptionMapper.toResponse(subscription);
	}

	@Override
	@PostMapping
	public long addSubscription(SubscriptionCreateDTO requestDTO) {
		Subscription subscription = subscriptionMapper.toEntity(requestDTO);
		return subscriptionInputAdapter.add(subscription);
	}

	@Override
	@PostMapping("/subscribe/{subscriptionId}/{customerId}")
	public long subscribeCustomer(@PathVariable long subscriptionId, @PathVariable long customerId) {
		return subscriptionInputAdapter.subscribeCustomer(subscriptionId, customerId);
	}

	@Override
	@PostMapping("/{subscriptionId}")
	public void resumeSubscription(@PathVariable Long subscriptionId) {
		subscriptionInputAdapter.resume(subscriptionId);
	}

	@Override
	@DeleteMapping("/{subscriptionId}")
	public void cancelSubscription(@PathVariable Long subscriptionId) {
		subscriptionInputAdapter.cancel(subscriptionId);
	}

	@Override
	@PutMapping("/discount/{subscriptionId}")
	public long updateDiscount(@PathVariable Long subscriptionId, long discount) {
		return subscriptionInputAdapter.updateDiscount(subscriptionId, discount);
	}

}
