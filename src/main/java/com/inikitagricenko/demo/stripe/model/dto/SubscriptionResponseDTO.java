package com.inikitagricenko.demo.stripe.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.inikitagricenko.demo.stripe.model.Customer;
import com.inikitagricenko.demo.stripe.model.Product;
import com.inikitagricenko.demo.stripe.model.Subscription;
import com.inikitagricenko.demo.stripe.model.enums.Currency;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link Subscription}
 */
@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class SubscriptionResponseDTO implements Serializable {
	Long id;
	String name;
	LocalDateTime cancelAt;
	LocalDateTime created;
	Currency currency;
	List<CustomerResponseDTO> customerList;
	String description;
	Long discount;
	List<ProductResponseDTO> productList;
	String status;
}