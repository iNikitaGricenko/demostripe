package com.inikitagricenko.demo.stripe.adapter;

import com.inikitagricenko.demo.stripe.model.CustomerOrder;
import com.inikitagricenko.demo.stripe.model.enums.OrderStatus;
import org.springframework.scheduling.annotation.Async;

public interface CustomerOrderInputAdapter {

	long pay(CustomerOrder customerOrder);

	void confirmPay(Long orderId);

	@Async
	void cancelPay(Long orderId);

	long updateDeliveryStatus(Long id, OrderStatus status);
}
