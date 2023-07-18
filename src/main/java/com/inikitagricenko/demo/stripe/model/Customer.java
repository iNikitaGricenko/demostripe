package com.inikitagricenko.demo.stripe.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Customer {

    private Long id;

    private String stripeReference;

    private String firstName;

    private String secondName;

    private String phone;

    private String email;

    private LocalDateTime registerDate;
}
