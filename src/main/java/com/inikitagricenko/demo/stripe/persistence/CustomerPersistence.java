package com.inikitagricenko.demo.stripe.persistence;

import com.inikitagricenko.demo.stripe.entity.CustomerEntity;
import com.inikitagricenko.demo.stripe.mapper.CustomerMapper;
import com.inikitagricenko.demo.stripe.model.Customer;
import com.inikitagricenko.demo.stripe.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerPersistence {

	private final CustomerRepository customerRepository;
	private final CustomerMapper customerMapper;

	public long save(Customer customer) {
		CustomerEntity entity = customerMapper.toEntity(customer);
		return customerRepository.save(entity).getId();
	}

	public Customer findById(long id) {
		return customerRepository.findById(id)
				.map(customerMapper::toCustomer)
				.orElseThrow(EntityNotFoundException::new);
	}

	public long update(long id, Customer customer) {
		CustomerEntity entity = customerRepository.findById(id).orElseThrow(EntityNotFoundException::new);
		CustomerEntity updated = customerMapper.partialUpdate(customer, entity);
		return customerRepository.save(updated).getId();
	}

	public List<Customer> findAll() {
		return customerRepository.findAll().stream().map(customerMapper::toCustomer).toList();
	}

	public void delete(long id) {
		customerRepository.deleteById(id);
	}
}
