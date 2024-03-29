package com.inikitagricenko.demo.stripe.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.io.Serializable;

@Data
public class OrderItemRequestDTO implements Serializable {
    @NotNull
    @Min(1)
    @Positive
    @Schema(example = "3")
    private final Long productId;

    @NotNull
    @Schema(example = "Intel Core i3")
    private final String name;

    @NotNull
    @Schema(example = "i83da")
    private final String model;

    @NotNull @Min(1)
    @Schema(example = "99.9")
    private float unitPrice;

    @NotNull @Min(1)
    private int quantity;
}
