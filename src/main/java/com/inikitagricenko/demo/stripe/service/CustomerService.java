package com.inikitagricenko.demo.stripe.service;

import com.inikitagricenko.demo.stripe.model.Customer;
import com.inikitagricenko.demo.stripe.persistence.CustomerPersistence;
import com.inikitagricenko.demo.stripe.service.interfaces.ICustomerService;
import com.inikitagricenko.demo.stripe.service.stripe.StripeCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService implements ICustomerService {

	private final StripeCustomerService stripeCustomerService;
	private final CustomerPersistence customerPersistence;

	@Override
	public Long addCustomer(Customer customer) {
		String reference = stripeCustomerService.addCustomer(customer.getEmail()).getId();
		customer.setStripeReference(reference);
		return customerPersistence.save(customer);
	}

	@Override
	public void deleteCustomer(Long customerId) {
		String reference = retrieve(customerId).getStripeReference();
		stripeCustomerService.deleteCustomer(reference);
		customerPersistence.delete(customerId);
	}

	@Override
	public List<Customer> retrieveAll() {
		return customerPersistence.findAll();
	}

	@Override
	public Customer retrieve(Long customerId) {
		return customerPersistence.findById(customerId);
	}

}
