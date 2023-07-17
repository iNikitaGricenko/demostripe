package com.inikitagricenko.demo.stripe.service.stripe;

import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.CustomerListParams;
import com.stripe.param.CustomerRetrieveParams;
import com.stripe.param.PaymentSourceCollectionCreateParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StripeCustomerService {

	public static final String MASTERCARD_SOURCE = "tok_mastercard";

	public Customer addCustomer(com.inikitagricenko.demo.stripe.model.Customer customer) {
		try {
			CustomerCreateParams customerCreateParams = CustomerCreateParams.builder()
					.setEmail(customer.getEmail())
					.setPhone(customer.getPhone())
					.setBalance(0L)
					.build();
			Customer created = Customer.create(customerCreateParams);

			CustomerRetrieveParams customerRetrieveParams = CustomerRetrieveParams.builder()
					.addExpand("sources")
					.build();
			Customer retrieved = Customer.retrieve(created.getId(), customerRetrieveParams, null);

			PaymentSourceCollectionCreateParams paymentSourceCollectionCreateParams = PaymentSourceCollectionCreateParams.builder()
					.setSource(MASTERCARD_SOURCE)
					.build();
			retrieved.getSources().create(paymentSourceCollectionCreateParams);

			return retrieved;
		} catch (StripeException e) {
			throw new RuntimeException(e);
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
