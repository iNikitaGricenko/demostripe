package com.inikitagricenko.demo.stripe.adapter;

import com.inikitagricenko.demo.stripe.model.CustomerOrder;
import com.inikitagricenko.demo.stripe.model.dto.AnalyticsResponse;
import com.inikitagricenko.demo.stripe.model.dto.AnalyticsSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerOrderOutputAdapter {
	Page<CustomerOrder> retrieveAll(Pageable pageable);

	CustomerOrder retrieve(long id);

	AnalyticsResponse getAnalytics(AnalyticsSearch analyticsSearch);
}
