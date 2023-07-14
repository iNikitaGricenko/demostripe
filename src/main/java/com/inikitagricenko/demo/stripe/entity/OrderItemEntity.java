package com.inikitagricenko.demo.stripe.entity;

import lombok.Data;

@Data
public class OrderItemEntity {

    private Long id;

    private String name;

    private String model;

    private long quantity;

    private long unitPrice;
}
