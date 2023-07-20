package com.inikitagricenko.demo.stripe.adapter;

import com.inikitagricenko.demo.stripe.model.Customer;

import java.util.List;

public interface CustomerOutputAdapter {

	List<Customer> retrieveAll();

	Customer retrieve(Long customerId);

	Customer retrieve(String email);

}
