package com.inikitagricenko.demo.stripe.adapter;

import com.inikitagricenko.demo.stripe.model.CustomerOrder;

public interface PaymentAdapter {

	String payment(CustomerOrder order, String token);

}