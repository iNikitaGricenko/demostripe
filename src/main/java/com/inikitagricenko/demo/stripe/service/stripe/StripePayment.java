package com.inikitagricenko.demo.stripe.service.stripe;

import com.inikitagricenko.demo.stripe.adapter.PaymentAdapter;
import com.inikitagricenko.demo.stripe.handler.error.DefaultBackendException;
import com.inikitagricenko.demo.stripe.model.Customer;
import com.inikitagricenko.demo.stripe.model.CustomerOrder;
import com.inikitagricenko.demo.stripe.service.interfaces.ICustomerService;
import com.inikitagricenko.demo.stripe.utils.StripeUtils;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
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
			String customerReference = Optional.ofNullable(order.getCustomer().getId())
					.map(customerService::retrieve)
					.map(Customer::getStripeReference)
					.orElse(customerService.retrieve(order.getCustomer().getEmail()).getStripeReference());

			stripePaymentMethodService.create(order, customerReference);

			stripeCardService.addCustomerCard(stripeCustomerService.retrieveWithSources(customerReference), order.getPaymentMethod());

			return Charge.create(stripeUtils.exctractChargeCreateParams(order, customerReference)).getId();
		} catch (StripeException e) {
			log.error("Payment on pay error occurs ", e);
			String email = order.getCustomer().getEmail();
			// TODO send failed email to user
			throw new DefaultBackendException(e);
		}
	}

	@Override
	public String confirm(String chargeReference) {
		try {
			Charge charge = Charge.retrieve(chargeReference);
			charge.capture();
			return charge.getId();
		} catch (StripeException e) {
			log.error("Payment on confirm error occurs ", e);
			throw new DefaultBackendException(e);
		}
	}

}
