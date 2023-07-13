package com.inikitagricenko.demo.stripe.service;

import com.inikitagricenko.demo.stripe.adapter.PaymentAdapter;
import com.inikitagricenko.demo.stripe.model.CustomerOrder;
import com.stripe.param.ChargeCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StripePayment implements PaymentAdapter {

	private final OrderService orderService;

	@Value("${stripe.keys.secret}")
	private String stripeSecretKey;

	@Override
	public String payment(CustomerOrder order, String token) {

		ChargeCreateParams createParams = ChargeCreateParams.builder()
				.setAmount(orderService.getTotalOrderPrice(order.getOrderItems()))
				.setCurrency(order.getPaymentCurrency().getAbbreviation())
				.setDescription(order.getDescription())
				.setSource(token)
				.build();

		return null;
	}

}
