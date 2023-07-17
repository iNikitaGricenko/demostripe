package com.inikitagricenko.demo.stripe.service;

import com.inikitagricenko.demo.stripe.config.annotations.PerformanceMonitor;
import com.inikitagricenko.demo.stripe.model.Customer;
import com.inikitagricenko.demo.stripe.persistence.CustomerPersistence;
import com.inikitagricenko.demo.stripe.service.interfaces.ICustomerService;
import com.inikitagricenko.demo.stripe.service.stripe.StripeCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService implements ICustomerService {

	private final StripeCustomerService stripeCustomerService;
	private final CustomerPersistence customerPersistence;

	@Override
	@PerformanceMonitor
	public Long addCustomer(Customer customer) {
		String reference = stripeCustomerService.addCustomer(customer).getId();
		customer.setStripeReference(reference);
		customer.setRegisterDate(LocalDateTime.now());
		return customerPersistence.save(customer);
	}

	@Override
	@PerformanceMonitor
	public void deleteCustomer(Long customerId) {
		String reference = retrieve(customerId).getStripeReference();
		stripeCustomerService.deleteCustomer(reference);
		customerPersistence.delete(customerId);
	}

	@Override
	@PerformanceMonitor
	public List<Customer> retrieveAll() {
		return customerPersistence.findAll();
	}

	@Override
	@PerformanceMonitor
	public Customer retrieve(Long customerId) {
		return customerPersistence.findById(customerId);
	}

}
