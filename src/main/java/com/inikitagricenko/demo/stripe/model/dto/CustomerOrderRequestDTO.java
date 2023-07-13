package com.inikitagricenko.demo.stripe.model.dto;

import com.inikitagricenko.demo.stripe.model.enums.Currency;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class CustomerOrderRequestDTO implements Serializable {
    @NotNull
    @Size(min = 5)
    private final String address;

    private final String description;

    @NotNull
    private final String paymentMethod;

    @NotNull
    private final Currency paymentCurrency;

    @NotNull
    private final String city;

    @NotNull
    private final String state;

    @NotNull
    private final String country;

    @NotNull
    private final String zipCode;

    @NotNull
    private final CustomerRequestDTO customer;

    @NotNull
    private final Set<OrderItemRequestDTO> orderItems;
}
