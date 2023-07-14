package com.inikitagricenko.demo.stripe.model.dto;

import java.io.Serializable;

public record AnalyticsResponse(double totalPrice,
                                long totalQuantity,
                                double maxOrderPrice,
                                double minOrderPrice) implements Serializable {
}
