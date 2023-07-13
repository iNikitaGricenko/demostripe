package com.inikitagricenko.demo.stripe.controller;

import com.inikitagricenko.demo.stripe.adapter.CustomerInputAdapter;
import com.inikitagricenko.demo.stripe.adapter.CustomerOutputAdapter;
import com.inikitagricenko.demo.stripe.mapper.CustomerMapper;
import com.inikitagricenko.demo.stripe.model.Customer;
import com.inikitagricenko.demo.stripe.model.dto.CustomerRequestDTO;
import com.inikitagricenko.demo.stripe.model.dto.CustomerResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@Tag(name = "Customer API")
@RequiredArgsConstructor
public class CustomerController {

	private final CustomerInputAdapter customerInputService;
	private final CustomerOutputAdapter customerOutputService;
	private final CustomerMapper customerMapper;

	@GetMapping
	public List<CustomerRequestDTO> retrieveAllCustomers() {
		List<Customer> customer = customerOutputService.retrieveAll();
		return customerMapper.toRequestDTO(customer);
	}

	@PostMapping
	public Long addCustomer(@RequestBody CustomerRequestDTO customerRequest) {
		Customer customer = customerMapper.toCustomerFromRequest(customerRequest);
		return customerInputService.addCustomer(customer);
	}

	@DeleteMapping("/{customerId}")
	public void deleteCustomer(@PathVariable Long customerId) {
		customerInputService.deleteCustomer(customerId);
	}

	@GetMapping("/{customerId}")
	public CustomerResponseDTO retrieveCustomer(@PathVariable Long customerId) {
		Customer retrieved = customerOutputService.retrieve(customerId);
		return customerMapper.toResponseDTO(retrieved);
	}

}
