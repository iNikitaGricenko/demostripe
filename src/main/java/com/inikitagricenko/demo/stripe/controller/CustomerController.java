package com.inikitagricenko.demo.stripe.controller;

import com.inikitagricenko.demo.stripe.adapter.CustomerInputAdapter;
import com.inikitagricenko.demo.stripe.adapter.CustomerOutputAdapter;
import com.inikitagricenko.demo.stripe.controller.interfaces.CustomerEndpoint;
import com.inikitagricenko.demo.stripe.mapper.CustomerMapper;
import com.inikitagricenko.demo.stripe.model.Customer;
import com.inikitagricenko.demo.stripe.model.dto.CustomerRequestDTO;
import com.inikitagricenko.demo.stripe.model.dto.CustomerResponseDTO;
import com.inikitagricenko.demo.stripe.wrapper.CustomerPage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController implements CustomerEndpoint {

	private final CustomerInputAdapter customerInputService;
	private final CustomerOutputAdapter customerOutputService;
	private final CustomerMapper customerMapper;

	@Override
	@GetMapping
	public CustomerPage retrieveAllCustomers(Pageable pageable) {
		Page<Customer> customer = customerOutputService.retrieveAll(pageable);
		return new CustomerPage(customer.map(customerMapper::toResponse));
	}

	@Override
	@PostMapping
	public Long addCustomer(CustomerRequestDTO customerRequest) {
		Customer customer = customerMapper.toCustomer(customerRequest);
		return customerInputService.addCustomer(customer);
	}

	@Override
	@DeleteMapping("/{customerId}")
	public void deleteCustomer(@PathVariable Long customerId) {
		customerInputService.deleteCustomer(customerId);
	}

	@Override
	@GetMapping("/{customerId}")
	public CustomerResponseDTO retrieveCustomer(@PathVariable Long customerId) {
		Customer retrieved = customerOutputService.retrieve(customerId);
		return customerMapper.toResponse(retrieved);
	}

}
