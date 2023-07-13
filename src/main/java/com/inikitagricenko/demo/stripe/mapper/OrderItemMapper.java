package com.inikitagricenko.demo.stripe.mapper;

import com.inikitagricenko.demo.stripe.model.OrderItem;
import com.inikitagricenko.demo.stripe.model.dto.OrderItemRequestDTO;
import com.inikitagricenko.demo.stripe.model.dto.OrderItemResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface OrderItemMapper {
    OrderItem toProductFromRequest(OrderItemRequestDTO orderItemRequestDTO);

    OrderItemRequestDTO toRequestDTO(OrderItem orderItem);

    OrderItem toProductFromResponse(OrderItemResponseDTO orderResponseDTO);

    OrderItemResponseDTO toResponseDTO(OrderItem order);
}
