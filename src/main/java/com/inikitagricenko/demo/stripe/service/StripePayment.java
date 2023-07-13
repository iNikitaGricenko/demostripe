package com.inikitagricenko.demo.stripe.service;

import com.inikitagricenko.demo.stripe.adapter.PaymentAdapter;
import com.inikitagricenko.demo.stripe.model.CustomerOrder;
import com.inikitagricenko.demo.stripe.utils.StripeUtils;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StripePayment implements PaymentAdapter {

	private final StripeUtils stripeUtils;

	@Override
	public String payment(CustomerOrder order, String token) {
		try {
			return Charge.create(stripeUtils.exctractChargeCreateParams(order, token)).getId();
		} catch (StripeException e) {
			String email = order.getCustomer().getEmail();
			// TODO send failed email to user
			throw new RuntimeException(e);
		}
	}

}
