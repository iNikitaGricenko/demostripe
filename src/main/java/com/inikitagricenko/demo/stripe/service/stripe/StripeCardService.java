package com.inikitagricenko.demo.stripe.service.stripe;

import com.inikitagricenko.demo.stripe.handler.error.DefaultBackendException;
import com.inikitagricenko.demo.stripe.model.enums.PaymentMethod;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.param.PaymentSourceCollectionCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StripeCardService {

	@Async
	public void addCustomerCard(Customer customer, PaymentMethod paymentMethod) {
		try {
			PaymentSourceCollectionCreateParams paymentSourceCollectionCreateParams = PaymentSourceCollectionCreateParams.builder()
					.setSource(paymentMethod.getSource())
					.build();
			customer.getSources().create(paymentSourceCollectionCreateParams);
		} catch (StripeException e) {
			throw new DefaultBackendException(e);
		}
	}

}
