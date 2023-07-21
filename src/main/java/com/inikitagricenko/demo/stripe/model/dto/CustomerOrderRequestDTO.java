package com.inikitagricenko.demo.stripe.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inikitagricenko.demo.stripe.model.Payment;
import com.inikitagricenko.demo.stripe.model.enums.Currency;
import com.inikitagricenko.demo.stripe.model.enums.PaymentMethod;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class CustomerOrderRequestDTO implements Serializable {
    @NotEmpty(message = "'address' should not be empty")
    @NotNull(message = "'address' should not be null")
    @Size(min = 5, message = "Address size should be minimum of 5 characters")
    private final String address;

    private final String description;

    @JsonProperty("payment_method")
    @NotNull(message = "'payment_method' should not be null")
    private final PaymentMethod paymentMethod;

    @JsonProperty("payment_currency")
    @NotNull(message = "'payment_currency' should not be null")
    private final Currency paymentCurrency;

    @NotEmpty(message = "'city' should not be empty")
    @NotNull(message = "'city' should not be null")
    private final String city;

    @NotEmpty(message = "'state' should not be empty")
    @NotNull(message = "'state' should not be null")
    private final String state;

    @NotEmpty(message = "'country' should not be empty")
    @NotNull(message = "'country' should not be null")
    private final String country;

    @JsonProperty("zip_code")
    @NotEmpty(message = "'zip_code' should not be empty")
    @NotNull(message = "'zip_code' should not be null")
    private final String zipCode;

    @NotNull(message = "'customer' should not be null")
    private final CustomerRequestDTO customer;

    @Valid
    @JsonProperty("order_items")
    @NotNull(message = "'order_items' should not be null")
    private final Set<OrderItemRequestDTO> orderItems;

    private final Payment payment;
}
