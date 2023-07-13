package com.inikitagricenko.demo.stripe.service;

import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.CustomerListParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StripeCustomerService {

	public Customer addCustomer(String email) {
		try {
			CustomerCreateParams customerCreateParams = CustomerCreateParams.builder()
					.setEmail(email)
					.build();
			return Customer.create(customerCreateParams);
		} catch (StripeException e) {
			throw new RuntimeException(e);
		}
	}

	public void deleteCustomer(String customerEmil) {
		try {
			retrieve(customerEmil).delete();
		} catch (StripeException e) {
			throw new RuntimeException(e);
		}
	}

	public Page<Customer> retrieveAll(Pageable pageable) {
		try {
			List<Customer> customers = new ArrayList<>();

			CustomerListParams customerListParams = CustomerListParams.builder()
					.setLimit((long) pageable.getPageSize())
					.build();

			List<Customer> data = Customer.list(customerListParams).getData();

			return new PageImpl<>(data, pageable, data.size());
		} catch (StripeException e) {
			throw new RuntimeException(e);
		}
	}

	public Customer retrieve(String customerId) {
		try {
			return Optional.ofNullable(Customer.retrieve(customerId)).orElseThrow(RuntimeException::new);
		} catch (StripeException e) {
			throw new RuntimeException(e);
		}
	}
}
