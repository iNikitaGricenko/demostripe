package com.inikitagricenko.demo.stripe.model;

import com.inikitagricenko.demo.stripe.model.enums.Currency;
import com.inikitagricenko.demo.stripe.model.enums.OrderStatus;
import com.inikitagricenko.demo.stripe.model.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CustomerOrder {

    private Long id;

    private String stripeReference;

    private PaymentMethod paymentMethod;

    private Currency paymentCurrency = Currency.EUR;

    private String address;

    private String city;

    private String state;

    private String country;

    private String zipCode;

    private String description;

    private LocalDateTime created;

    private OrderStatus status;

    private LocalDateTime completed;

    private Customer customer;

    private Set<OrderItem> orderItems;

    private Payment payment;

    public boolean isFinalDeal() {
        return OrderStatus.DELIVERED.equals(this.status) || OrderStatus.RETURNED.equals(this.status);
    }
}
