package com.inikitagricenko.demo.stripe.model;

import com.inikitagricenko.demo.stripe.model.enums.Currency;
import com.inikitagricenko.demo.stripe.model.enums.OrderStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CustomerOrder {

    @EqualsAndHashCode.Exclude
    private Long id;

    private String paymentMethod;

    private Currency paymentCurrency = Currency.EUR;

    private String address;

    private String city;

    private String state;

    private String country;

    private String zipCode;

    private String description;

    @Builder.Default
    @EqualsAndHashCode.Exclude
    private LocalDateTime created = LocalDateTime.now();

    @Builder.Default
    private OrderStatus status = OrderStatus.INPROGRESS;

    private LocalDateTime completed;

    private Customer customer;

    private boolean isDeleted = false;

    private LocalDateTime deletedAt;

    private Set<OrderItem> orderItems;
}
