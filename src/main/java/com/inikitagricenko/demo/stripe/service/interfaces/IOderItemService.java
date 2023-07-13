package com.inikitagricenko.demo.stripe.service.interfaces;

import com.inikitagricenko.demo.stripe.model.OrderItem;

import java.util.Collection;

public interface IOderItemService {
	double getMinPriceForItem(Collection<OrderItem> orderItems);

	double getMaxPriceForItem(Collection<OrderItem> orderItems);

	long getTotalOrderQuantity(Collection<OrderItem> orderItems);

	double getTotalOrderPrice(Collection<OrderItem> orderItems);
}
