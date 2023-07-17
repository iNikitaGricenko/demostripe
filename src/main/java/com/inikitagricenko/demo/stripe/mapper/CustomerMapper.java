package com.inikitagricenko.demo.stripe.mapper;

import com.inikitagricenko.demo.stripe.entity.CustomerEntity;
import com.inikitagricenko.demo.stripe.model.Customer;
import com.inikitagricenko.demo.stripe.model.dto.CustomerRequestDTO;
import com.inikitagricenko.demo.stripe.model.dto.CustomerResponseDTO;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CustomerMapper {
	Customer toCustomer(CustomerRequestDTO request);

	Customer toCustomer(CustomerResponseDTO response);

	Customer toCustomer(CustomerEntity entity);

	CustomerEntity toEntity(Customer customer);

	CustomerRequestDTO toRequest(Customer customer);

	List<CustomerRequestDTO> toRequest(List<Customer> customer);


	CustomerResponseDTO toResponse(Customer customer);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	CustomerEntity partialUpdate(Customer updater, @MappingTarget CustomerEntity entity);
}
