package com.inikitagricenko.demo.stripe.service.stripe;

import com.inikitagricenko.demo.stripe.adapter.PaymentAdapter;
import com.inikitagricenko.demo.stripe.handler.error.DefaultBackendException;
import com.inikitagricenko.demo.stripe.model.CustomerOrder;
import com.inikitagricenko.demo.stripe.service.interfaces.ICustomerService;
import com.inikitagricenko.demo.stripe.utils.StripeUtils;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StripePayment implements PaymentAdapter {

	private final StripeUtils stripeUtils;
	private final StripePaymentMethodService stripePaymentMethodService;
	private final ICustomerService customerService;
	private final StripeCustomerService stripeCustomerService;
	private final StripeCardService stripeCardService;

	@Override
	public String pay(CustomerOrder order) {
		try {
			String customerReference = customerService.retrieve(order.getCustomer().getEmail()).getStripeReference();

			stripePaymentMethodService.create(order, customerReference);

			stripeCardService.addCustomerCard(stripeCustomerService.retrieveWithSources(customerReference), order.getPaymentMethod());

			return Charge.create(stripeUtils.exctractChargeCreateParams(order, customerReference)).getId();
		} catch (StripeException e) {
			String email = order.getCustomer().getEmail();
			// TODO send failed email to user
			throw new DefaultBackendException(e);
		}
	}

}
