package com.inikitagricenko.demo.stripe.service.stripe;

import com.inikitagricenko.demo.stripe.handler.error.DefaultBackendException;
import com.inikitagricenko.demo.stripe.model.enums.PaymentMethod;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.param.PaymentSourceCollectionCreateParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class StripeCardService {

	public String addCustomerCard(Customer customer, PaymentMethod paymentMethod) {
		try {
			PaymentSourceCollectionCreateParams paymentSourceCollectionCreateParams = PaymentSourceCollectionCreateParams.builder()
					.setSource(paymentMethod.getSource())
					.build();
			return customer.getSources().create(paymentSourceCollectionCreateParams).getId();
		} catch (StripeException e) {
			throw new DefaultBackendException(e);
		}
	}

}
