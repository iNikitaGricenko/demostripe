package com.inikitagricenko.demo.stripe.service;

import com.inikitagricenko.demo.stripe.config.annotations.PerformanceMonitor;
import com.inikitagricenko.demo.stripe.model.CustomerOrder;
import com.inikitagricenko.demo.stripe.model.OrderItem;
import com.inikitagricenko.demo.stripe.model.dto.AnalyticsResponse;
import com.inikitagricenko.demo.stripe.service.interfaces.IAnalyticsService;
import com.inikitagricenko.demo.stripe.service.interfaces.IOderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalyticsService implements IAnalyticsService {

	private final IOderItemService orderItemService;

	@Override
	@PerformanceMonitor
	public AnalyticsResponse getAnalytic(List<OrderItem> orderItems) {
		long totalOrderQuantity = orderItemService.getTotalOrderQuantity(orderItems);
		double totalOrderPrice = orderItemService.getTotalOrderPrice(orderItems);
		double maxPriceForItem = orderItemService.getMaxPriceForItem(orderItems);
		double minPriceForItem = orderItemService.getMinPriceForItem(orderItems);

		return new AnalyticsResponse(totalOrderPrice, totalOrderQuantity, maxPriceForItem, minPriceForItem);
	}



}
