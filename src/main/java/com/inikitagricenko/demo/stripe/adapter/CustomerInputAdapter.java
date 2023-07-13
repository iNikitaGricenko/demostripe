package com.inikitagricenko.demo.stripe.adapter;

import com.inikitagricenko.demo.stripe.model.Customer;

public interface CustomerInputAdapter {

	Long addCustomer(Customer customer);

	void deleteCustomer(Long customerId);

}
