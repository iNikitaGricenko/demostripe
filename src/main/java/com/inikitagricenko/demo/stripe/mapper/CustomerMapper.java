package com.inikitagricenko.demo.stripe.mapper;

import com.inikitagricenko.demo.stripe.model.Customer;
import com.inikitagricenko.demo.stripe.model.dto.CustomerRequestDTO;
import com.inikitagricenko.demo.stripe.model.dto.CustomerResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CustomerMapper {
    Customer toCustomerFromRequest(CustomerRequestDTO customerRequestDTO);

    CustomerRequestDTO toRequestDTO(Customer customer);

    List<CustomerRequestDTO> toRequestDTO(List<Customer> customer);

    Customer toCustomerFromResponse(CustomerResponseDTO customerResponseDTO);

    CustomerResponseDTO toResponseDTO(Customer customer);
}
