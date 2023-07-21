package com.inikitagricenko.demo.stripe.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inikitagricenko.demo.stripe.model.Subscription;
import com.inikitagricenko.demo.stripe.model.enums.Currency;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link Subscription}
 */
@Value
public class SubscriptionCreateDTO implements Serializable {
	Long id;

	@NotNull(message = "Subscription 'name' should not be null")
	@NotEmpty(message = "Subscription 'name' should not be empty")
	String name;

	@NotNull(message = "Subscription 'currency' should not be null")
	Currency currency;

	@NotNull(message = "Subscription 'description' should not be null")
	String description;

	@NotNull(message = "Subscription 'discount' should not be null")
	Long discount;

	@JsonProperty("products")
	@NotEmpty(message = "Subscription 'products' should not be empty")
	@NotNull(message = "Subscription 'products' should not be null")
	List<ProductRequestDTO> productList;
}