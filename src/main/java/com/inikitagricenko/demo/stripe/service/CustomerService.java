package com.inikitagricenko.demo.stripe.service;

import com.inikitagricenko.demo.stripe.adapter.CustomerInputAdapter;
import com.inikitagricenko.demo.stripe.adapter.CustomerOutputAdapter;
import com.inikitagricenko.demo.stripe.model.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService implements CustomerInputAdapter, CustomerOutputAdapter {

	private final StripeCustomerService stripeCustomerService;

	@Override
	public Long addCustomer(Customer customer) {
		stripeCustomerService.addCustomer(customer.getEmail());
		// TODO Save customer in database
		return null; // TODO Return customerId from database
	}

	@Override
	public void deleteCustomer(Long customerId) {
		// TODO Retrieve customer email from database
		// TODO Delete customer from stripe stripeCustomerService.deleteCustomer(customer.getEmail());
		// TODO Delete customer from database
	}

	@Override
	public List<Customer> retrieveAll() {
		return new ArrayList<>(); // TODO Retrieve all customers from database -> stripe
	}

	@Override
	public Customer retrieve(Long customerId) {
		return null; // TODO Retrieve customer from database -> stripe
	}

}
