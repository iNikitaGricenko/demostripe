package com.inikitagricenko.demo.stripe.model;

import lombok.*;

import java.time.LocalDateTime;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @EqualsAndHashCode.Exclude
    private Long id;

    private String firstName;

    private String secondName;

    private String phone;

    private String email;

    @EqualsAndHashCode.Exclude
    private LocalDateTime registerDate = LocalDateTime.now();
}
