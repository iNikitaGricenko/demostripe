package com.inikitagricenko.demo.stripe.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.io.Serializable;

@Data
public class OrderItemRequestDTO implements Serializable {
    @JsonProperty("id")
    @Schema(example = "3")
    @NotNull(message = "product 'id' should not be null")
    @Min(value = 1, message = "product 'id' should be more than 0")
    @Positive(message = "product 'id' should be positive")
    private final Long productId;

    @NotNull(message = "product 'name' should not be null")
    @NotEmpty(message = "product 'name' should not be empty")
    @Schema(example = "Intel Core i3")
    private final String name;

    @NotNull(message = "product 'id' should not be null")
    @NotEmpty(message = "product 'model' should not be empty")
    @Schema(example = "i83da")
    private final String model;

    @JsonProperty("unit_price")
    @Schema(example = "99.9")
    @Min(value = 1, message = "product 'unit_price' should be minimum 1")
    @Positive(message = "product 'unit_price' should be positive")
    @NotNull(message = "product 'unit_price' should not be null")
    private float unitPrice;

    @Schema(example = "1")
    @Min(value = 1, message = "product 'quantity' should be minimum 1")
    @Positive(message = "product 'quantity' should be positive")
    @NotNull(message = "product 'quantity' should not be null")
    private int quantity;
}
