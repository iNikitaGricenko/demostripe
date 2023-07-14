package com.inikitagricenko.demo.stripe.model.dto;

import com.inikitagricenko.demo.stripe.model.OrderItem;
import com.inikitagricenko.demo.stripe.model.Product;
import com.inikitagricenko.demo.stripe.model.Subscription;
import com.inikitagricenko.demo.stripe.model.enums.Currency;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link Subscription}
 */
@Value
public class SubscriptionRequestDTO implements Serializable {
	String name;
	Currency currency;
	String description;
	Long discount;
	List<Product> productList;
	Long customerId;
}