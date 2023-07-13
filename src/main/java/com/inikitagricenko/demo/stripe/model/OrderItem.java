package com.inikitagricenko.demo.stripe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

    private Long id;

    private String name;

    private String model;

    private long quantity;

    private long unitPrice;

    private String className;
}
