package com.inikitagricenko.demo.stripe.service;

import com.inikitagricenko.demo.stripe.adapter.OrdersAnalyticOutput;
import com.inikitagricenko.demo.stripe.model.AnalyticsResponse;
import com.inikitagricenko.demo.stripe.model.CustomerOrder;
import com.inikitagricenko.demo.stripe.model.OrderItem;
import com.inikitagricenko.demo.stripe.service.interfaces.IOderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalyticsService implements OrdersAnalyticOutput {

	private final IOderItemService orderItemService;

	@Override
	public AnalyticsResponse getAnalytic(List<CustomerOrder> customerOrders) {
		List<OrderItem> orderItems = new ArrayList<>();
		customerOrders.stream().map(CustomerOrder::getOrderItems).forEach(orderItems::addAll);

		long totalOrderQuantity = orderItemService.getTotalOrderQuantity(orderItems);
		double totalOrderPrice = orderItemService.getTotalOrderPrice(orderItems);
		double maxPriceForItem = orderItemService.getMaxPriceForItem(orderItems);
		double minPriceForItem = orderItemService.getMinPriceForItem(orderItems);

		return new AnalyticsResponse(totalOrderPrice, totalOrderQuantity, maxPriceForItem, minPriceForItem);
	}



}
