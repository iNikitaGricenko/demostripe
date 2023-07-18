package com.inikitagricenko.demo.stripe.persistence;

import com.inikitagricenko.demo.stripe.entity.CustomerOrderEntity;
import com.inikitagricenko.demo.stripe.mapper.CustomerOrderMapper;
import com.inikitagricenko.demo.stripe.model.CustomerOrder;
import com.inikitagricenko.demo.stripe.repository.CustomerOrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerOrderPersistence {

	private final CustomerOrderRepository customerRepository;
	private final CustomerOrderMapper customerMapper;

	public long save(CustomerOrder customer) {
		CustomerOrderEntity entity = customerMapper.toEntity(customer);
		return customerRepository.save(entity).getId();
	}

	public CustomerOrder findById(long id) {
		return customerRepository.findById(id)
				.map(customerMapper::toOrder)
				.orElseThrow(EntityNotFoundException::new);
	}

	public long update(long id, CustomerOrder customer) {
		CustomerOrderEntity entity = customerRepository.findById(id).orElseThrow(EntityNotFoundException::new);
		CustomerOrderEntity updated = customerMapper.partialUpdate(customer, entity);
		return customerRepository.save(updated).getId();
	}

	public List<CustomerOrder> findAll() {
		return customerRepository.findAll().stream().map(customerMapper::toOrder).toList();
	}

	public void delete(long id) {
		customerRepository.deleteById(id);
	}
}
