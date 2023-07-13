package com.inikitagricenko.demo.stripe.service;

import com.inikitagricenko.demo.stripe.model.OrderItem;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class OrderService {

	public double getMinPriceForItem(Collection<OrderItem> orderItems) {
		return orderItems.stream().mapToDouble(orderItem -> orderItem.getUnitPrice() * orderItem.getQuantity()).min().orElse(0);
	}

	public double getMaxPriceForItem(Collection<OrderItem> orderItems) {
		return orderItems.stream().mapToDouble(orderItem -> orderItem.getUnitPrice() * orderItem.getQuantity()).max().orElse(0);
	}

	public long getTotalOrderQuantity(Collection<OrderItem> orderItems) {
		return orderItems.stream().mapToLong(OrderItem::getQuantity).sum();
	}

	public long getTotalOrderPrice(Collection<OrderItem> orderItems) {
		return orderItems.stream().mapToLong(orderItem -> orderItem.getUnitPrice() * orderItem.getQuantity()).sum();
	}

}
