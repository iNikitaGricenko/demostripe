package com.inikitagricenko.demo.stripe.mapper;

import com.inikitagricenko.demo.stripe.model.CustomerOrder;
import com.inikitagricenko.demo.stripe.model.dto.CustomerOrderRequestDTO;
import com.inikitagricenko.demo.stripe.model.dto.CustomerOrderResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CustomerOrderMapper {
    CustomerOrder toOrderFromRequest(CustomerOrderRequestDTO customerOrderRequestDTO);

    CustomerOrderRequestDTO toRequestDTO(CustomerOrder customerOrder);

    CustomerOrder toOrderFromResponse(CustomerOrderResponseDTO customerOrderResponseDTO);

    CustomerOrderResponseDTO toResponseDTO(CustomerOrder customerOrder);

}