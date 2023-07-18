package com.inikitagricenko.demo.stripe.service.stripe;

import com.inikitagricenko.demo.stripe.handler.error.DefaultBackendException;
import com.inikitagricenko.demo.stripe.handler.error.InvalidPaymentMethod;
import com.inikitagricenko.demo.stripe.model.CustomerOrder;
import com.inikitagricenko.demo.stripe.model.Payment;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentMethod;
import com.stripe.param.PaymentMethodAttachParams;
import com.stripe.param.PaymentMethodCreateParams;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

import static com.stripe.param.PaymentMethodCreateParams.*;

@Service
public class StripePaymentMethodService {

	@Async
	public CompletableFuture<PaymentMethod> create(CustomerOrder order, String customer) {
		return CompletableFuture.supplyAsync(() -> {
			try {
				Payment payment = order.getPayment();
				if (!order.getPaymentMethod().validate(payment)) {
					throw new InvalidPaymentMethod("Invalid payment method");
				}

				PaymentMethodCreateParams paymentMethodCreateParams = builder()
						.setType(Type.CARD)
						.setCard(getCardDetails(payment))
						.build();

				PaymentMethod paymentMethod = PaymentMethod.create(paymentMethodCreateParams);
				PaymentMethodAttachParams paymentMethodAttachParams = PaymentMethodAttachParams.builder()
						.setCustomer(customer)
						.build();
				return paymentMethod.attach(paymentMethodAttachParams);
			} catch (StripeException e) {
				throw new DefaultBackendException(e);
			}
		});
	}

	public CardDetails getCardDetails(Payment payment) {
		return CardDetails.builder()
				.setNumber(payment.getCardNumber())
				.setExpYear(Long.valueOf(payment.getExpirationYear()))
				.setExpMonth(Long.valueOf(payment.getExpirationMonth()))
				.setCvc(payment.getCvc())
				.build();
	}

}
