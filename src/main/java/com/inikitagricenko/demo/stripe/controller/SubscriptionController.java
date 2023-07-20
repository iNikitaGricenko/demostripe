package com.inikitagricenko.demo.stripe.controller;

import com.inikitagricenko.demo.stripe.adapter.SubscriptionInputAdapter;
import com.inikitagricenko.demo.stripe.adapter.SubscriptionOutputAdapter;
import com.inikitagricenko.demo.stripe.mapper.SubscriptionMapper;
import com.inikitagricenko.demo.stripe.model.Subscription;
import com.inikitagricenko.demo.stripe.model.dto.SubscriptionRequestDTO;
import com.inikitagricenko.demo.stripe.model.dto.SubscriptionResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscription")
@Tag(name = "Subscription API")
@RequiredArgsConstructor
public class SubscriptionController {

	private final SubscriptionInputAdapter subscriptionInputAdapter;
	private final SubscriptionOutputAdapter subscriptionOutputAdapter;
	private final SubscriptionMapper subscriptionMapper;

	@GetMapping
	public @ResponseBody List<SubscriptionResponseDTO> retrieveSubscriptions() {
		List<Subscription> subscriptions = subscriptionOutputAdapter.retrieveAll();
		return subscriptionMapper.toResponse(subscriptions);
	}

	@GetMapping("/{subscriptionId}")
	public @ResponseBody SubscriptionResponseDTO retrieveSubscription(@PathVariable Long subscriptionId) {
		Subscription subscription = subscriptionOutputAdapter.retrieve(subscriptionId);
		return subscriptionMapper.toResponse(subscription);
	}

	@PostMapping
	public Long addSubscription(@Valid @RequestBody SubscriptionRequestDTO requestDTO) {
		Subscription subscription = subscriptionMapper.toEntity(requestDTO);
		return subscriptionInputAdapter.add(subscription);
	}

	@DeleteMapping("/{subscriptionId}")
	public void cancelSubscription(@PathVariable Long subscriptionId) {
		subscriptionInputAdapter.cancel(subscriptionId);
	}

}
