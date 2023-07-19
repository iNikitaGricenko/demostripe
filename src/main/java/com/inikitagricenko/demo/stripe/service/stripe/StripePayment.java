package com.inikitagricenko.demo.stripe.service.stripe;

import com.inikitagricenko.demo.stripe.adapter.PaymentAdapter;
import com.inikitagricenko.demo.stripe.config.annotations.PaymentValidation;
import com.inikitagricenko.demo.stripe.handler.error.DefaultBackendException;
import com.inikitagricenko.demo.stripe.model.Customer;
import com.inikitagricenko.demo.stripe.model.CustomerOrder;
import com.inikitagricenko.demo.stripe.model.Payment;
import com.inikitagricenko.demo.stripe.service.interfaces.ICustomerService;
import com.inikitagricenko.demo.stripe.utils.StripeUtils;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
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
		Customer entityCustomer = Optional.ofNullable(order.getCustomer().getId())
				.map(customerService::retrieve)
				.orElse(customerService.retrieve(order.getCustomer().getEmail()));
		String customerReference = entityCustomer.getStripeReference();

		try {
			createPayment(order, entityCustomer, customerReference);

			return Charge.create(stripeUtils.exctractChargeCreateParams(order, customerReference)).getId();
		} catch (StripeException e) {
			String email = entityCustomer.getEmail();
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
			throw new DefaultBackendException(e);
		}
	}

	private void createPayment(CustomerOrder order, Customer entityCustomer, String customerReference) {
		if (StringUtils.isEmpty((entityCustomer.getCard()))) {
			stripePaymentMethodService.create(order, customerReference);

			com.stripe.model.Customer withSources = stripeCustomerService.retrieveWithSources(customerReference);
			String cardReference = stripeCardService.addCustomerCard(withSources, order.getPaymentMethod());

			entityCustomer.setCard(cardReference);
			customerService.update(entityCustomer.getId(), entityCustomer);
		}
	}

}
