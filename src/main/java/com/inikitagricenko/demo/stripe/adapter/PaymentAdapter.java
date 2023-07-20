package com.inikitagricenko.demo.stripe.adapter;

import com.inikitagricenko.demo.stripe.model.CustomerOrder;

public interface PaymentAdapter {

	String pay(CustomerOrder order);

	String confirm(String charge);

	String cancel(String chargeReference, Long refundedAmount);
}
