package com.inikitagricenko.demo.stripe.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inikitagricenko.demo.stripe.model.Subscription;
import com.inikitagricenko.demo.stripe.model.enums.Currency;
import jakarta.validation.constraints.*;
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

	@Min(value = 0, message = "Subscription 'discount' should not be minimum 0")
	@Max(value = 100, message = "Subscription 'discount' should be maximum 100")
	@Positive(message = "Subscription 'discount' should be positive number")
	@NotNull(message = "Subscription 'discount' should not be null")
	Long discount;

	@JsonProperty("products")
	@NotEmpty(message = "Subscription 'products' should not be empty")
	@NotNull(message = "Subscription 'products' should not be null")
	List<ProductRequestDTO> productList;
}