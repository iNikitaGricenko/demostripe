package com.inikitagricenko.demo.stripe.service.stripe;

import com.inikitagricenko.demo.stripe.handler.error.DefaultBackendException;
import com.inikitagricenko.demo.stripe.model.enums.PaymentMethod;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.CustomerListParams;
import com.stripe.param.CustomerRetrieveParams;
import com.stripe.param.PaymentSourceCollectionCreateParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class StripeCustomerService {

	private final StripeCardService stripeCardService;

	public Customer addCustomer(com.inikitagricenko.demo.stripe.model.Customer customer) {
		try {
			CustomerCreateParams customerCreateParams = CustomerCreateParams.builder()
					.setEmail(customer.getEmail())
					.setPhone(customer.getPhone())
					.setBalance(0L)
					.build();
			Customer created = Customer.create(customerCreateParams);

			stripeCardService.addCustomerCard(retrieveWithSources(created.getId()), PaymentMethod.MASTERCARD);

			return created;
		} catch (StripeException e) {
			throw new DefaultBackendException(e);
		}
	}

	public void deleteCustomer(String customerId) {
		try {
			retrieve(customerId).delete();
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
			throw new DefaultBackendException(e);
		}
	}

	public Customer retrieve(String customerId) {
		try {
			return Optional.ofNullable(Customer.retrieve(customerId)).orElseThrow(RuntimeException::new);
		} catch (StripeException e) {
			log.error("Customer on retrieve error occurs ", e);
			throw new DefaultBackendException(e);
		}
	}

	public Customer retrieveWithSources(String customer) {
		try {
			CustomerRetrieveParams customerRetrieveParams = CustomerRetrieveParams.builder()
					.addExpand("sources")
					.build();
			return Customer.retrieve(customer, customerRetrieveParams, null);
		} catch (StripeException e) {
			throw new DefaultBackendException(e);
		}
	}
}
