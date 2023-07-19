package com.inikitagricenko.demo.stripe.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
public class Customer {

    private Long id;

    private String stripeReference;

    private String firstName;

    private String secondName;

    private String phone;

    private String email;

		private String card;

    private LocalDateTime registerDate;
}
