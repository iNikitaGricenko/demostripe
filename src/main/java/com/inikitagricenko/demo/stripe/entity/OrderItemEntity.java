package com.inikitagricenko.demo.stripe.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class OrderItemEntity {

    private Long id;

    private String name;

    private String model;

    private long quantity;

    private long unitPrice;
}
