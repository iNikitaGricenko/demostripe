package com.inikitagricenko.demo.stripe.adapter;

import com.inikitagricenko.demo.stripe.model.CustomerOrder;
import com.inikitagricenko.demo.stripe.model.enums.OrderStatus;

public interface CustomerOrderInputAdapter {

	long pay(CustomerOrder customerOrder);

	long updateStatus(Long id, OrderStatus status);
}
