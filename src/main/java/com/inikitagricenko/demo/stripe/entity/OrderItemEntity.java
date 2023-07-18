package com.inikitagricenko.demo.stripe.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class OrderItemEntity {

    private Long id;

    private Long productId;

    private String name;

    private String model;

    private long quantity;

    private float unitPrice;
}
