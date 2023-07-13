package com.inikitagricenko.demo.stripe.model.enums;

public enum OrderStatus {
    INPROGRESS, PENDING, DELIVERED, RETURNED;

    @Override
    public String toString() {
        return name();
    }
}
