package com.inikitagricenko.demo.stripe.adapter;

import com.inikitagricenko.demo.stripe.model.CustomerOrder;
import com.inikitagricenko.demo.stripe.model.dto.AnalyticsResponse;

import java.util.List;

public interface OrdersAnalyticOutput {

	AnalyticsResponse getAnalytic(List<CustomerOrder> customerOrderList);

}
