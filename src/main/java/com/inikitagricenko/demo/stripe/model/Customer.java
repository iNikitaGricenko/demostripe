package com.inikitagricenko.demo.stripe.model;

import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDateTime;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    private Long id;

    private String stripeReference;

    private String firstName;

    private String secondName;

    private String phone;

    private String email;

    private LocalDateTime registerDate;
}
