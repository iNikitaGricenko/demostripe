package com.inikitagricenko.demo.stripe.adapter;

import com.inikitagricenko.demo.stripe.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerOutputAdapter {

	Page<Customer> retrieveAll(Pageable pageable);

	Customer retrieve(Long customerId);

	Customer retrieve(String email);

}
