package com.inikitagricenko.demo.stripe.adapter;

import com.inikitagricenko.demo.stripe.model.CustomerOrder;
import com.inikitagricenko.demo.stripe.model.dto.AnalyticsResponse;
import com.inikitagricenko.demo.stripe.model.dto.AnalyticsSearch;

import java.util.List;

public interface CustomerOrderOutputAdapter {
	List<CustomerOrder> retrieveAll();

	CustomerOrder retrieve(long id);

	AnalyticsResponse getAnalytics(AnalyticsSearch analyticsSearch);
}
