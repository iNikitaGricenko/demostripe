package com.inikitagricenko.demo.stripe.service;

import com.inikitagricenko.demo.stripe.model.OrderItem;
import com.inikitagricenko.demo.stripe.service.interfaces.IOderItemService;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class OrderItemService implements IOderItemService {

	@Override
	public double getMinPriceForItem(Collection<OrderItem> orderItems) {
		return orderItems.stream().mapToDouble(orderItem -> orderItem.getUnitPrice() * orderItem.getQuantity()).map(this::removeRounding).min().orElse(0);
	}

	@Override
	public double getMaxPriceForItem(Collection<OrderItem> orderItems) {
		return orderItems.stream().mapToDouble(orderItem -> orderItem.getUnitPrice() * orderItem.getQuantity()).map(this::removeRounding).max().orElse(0);
	}

	@Override
	public long getTotalOrderQuantity(Collection<OrderItem> orderItems) {
		return orderItems.stream().mapToLong(OrderItem::getQuantity).sum();
	}

	@Override
	public double getTotalOrderPrice(Collection<OrderItem> orderItems) {
		return orderItems.stream().mapToDouble(orderItem -> orderItem.getUnitPrice() * orderItem.getQuantity()).map(this::removeRounding).sum();
	}

	private double removeRounding(double price) {
		double round = 0.001;
		return price / round;
	}

}
