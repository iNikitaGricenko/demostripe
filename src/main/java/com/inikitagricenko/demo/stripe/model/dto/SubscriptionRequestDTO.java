package com.inikitagricenko.demo.stripe.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inikitagricenko.demo.stripe.model.Customer;
import com.inikitagricenko.demo.stripe.model.Product;
import com.inikitagricenko.demo.stripe.model.Subscription;
import com.inikitagricenko.demo.stripe.model.enums.Currency;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link Subscription}
 */
@Value
public class SubscriptionRequestDTO implements Serializable {
	Long id;

	@NotNull(message = "name should not be null")
	@NotEmpty
	String name;

	@NotNull(message = "currency should not be null")
	Currency currency;

	@NotNull(message = "description should not be null")
	String description;

	@NotNull(message = "discount should not be null")
	Long discount;

	@NotEmpty
	@NotNull(message = "products should not be null")
	@JsonProperty("products")
	List<ProductRequestDTO> productList;

	@NotNull(message = "customer should not be null")
	Customer customer;
}