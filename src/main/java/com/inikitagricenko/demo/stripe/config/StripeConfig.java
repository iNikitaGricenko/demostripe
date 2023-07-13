package com.inikitagricenko.demo.stripe.config;

import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConfig {

	@Value("${STRIPE_SECRET_KEY}")
	private String stripeSecretKey;

	@PostConstruct
	void init() {
		Stripe.apiKey = stripeSecretKey;
	}

}
