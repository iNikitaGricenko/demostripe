package com.inikitagricenko.demo.stripe.adapter;

import com.inikitagricenko.demo.stripe.model.CustomerOrder;

import java.util.List;

public interface CustomerOrderOutputAdapter {
	List<CustomerOrder> retrieveAll();

	CustomerOrder retrieve(long id);
}
