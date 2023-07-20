package com.inikitagricenko.demo.stripe.model.enums;

public enum OrderStatus {
    STARTED, INPROGRESS, PENDING, DELIVERED, RETURNED, REFUNDED;

    @Override
    public String toString() {
        return name();
    }
}
