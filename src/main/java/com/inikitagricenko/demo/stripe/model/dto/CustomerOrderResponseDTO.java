package com.inikitagricenko.demo.stripe.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.inikitagricenko.demo.stripe.model.enums.Currency;
import com.inikitagricenko.demo.stripe.model.enums.OrderStatus;
import com.inikitagricenko.demo.stripe.model.enums.PaymentMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerOrderResponseDTO implements Serializable {
    @Schema(example = "1")
    private final Long id;

    @Schema(example = "st. New-York")
    private final String address;

    @Schema(example = "my descriptio")
    private final String description;

    @Schema(example = "MASTERCARD")
    private final PaymentMethod paymentMethod;

    @Schema(example = "EUR")
    private final Currency paymentCurrency;

    @Schema(example = "2007-12-03T10:15:30")
    private final LocalDateTime created;

    @Schema(example = "INPROGRESS")
    private final OrderStatus status;

    private final CustomerResponseDTO customer;

    @Schema(example = "false")
    private final boolean isDeleted;

    @Schema(example = "2007-12-03T10:15:30")
    private final LocalDateTime deletedAt;

    private final Set<OrderItemResponseDTO> orderItems;
}
