package com.inikitagricenko.demo.stripe.mapper;

import com.inikitagricenko.demo.stripe.entity.CustomerOrderEntity;
import com.inikitagricenko.demo.stripe.model.CustomerOrder;
import com.inikitagricenko.demo.stripe.model.dto.CustomerOrderRequestDTO;
import com.inikitagricenko.demo.stripe.model.dto.CustomerOrderResponseDTO;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CustomerOrderMapper {
    CustomerOrder toOrder(CustomerOrderRequestDTO customerOrderRequestDTO);

    CustomerOrderRequestDTO toRequest(CustomerOrder customerOrder);

    CustomerOrder toOrder(CustomerOrderResponseDTO customerOrderResponseDTO);

    CustomerOrderResponseDTO toResponse(CustomerOrder customerOrder);

    List<CustomerOrderResponseDTO> toResponse(List<CustomerOrder> customerOrder);

    CustomerOrderEntity toEntity(CustomerOrder customer);

    CustomerOrder toOrder(CustomerOrderEntity customerOrderEntity);

    List<CustomerOrder> toOrders(List<CustomerOrderEntity> customerOrderEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CustomerOrderEntity partialUpdate(CustomerOrder updater, @MappingTarget CustomerOrderEntity entity);
}