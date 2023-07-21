package com.inikitagricenko.demo.stripe.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.inikitagricenko.demo.stripe.entity.ProductEntity;
import com.inikitagricenko.demo.stripe.model.enums.Currency;
import jakarta.validation.constraints.*;
import lombok.Value;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * DTO for {@link ProductEntity}
 */
@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductRequestDTO implements Serializable {
	Long id;

	@NotNull(message = "Product 'name' should not be null")
	@NotEmpty(message = "Product 'name' should not be empty")
	@NotBlank(message = "Product 'name' should not be blank")
	@Length(min = 8, message = "Product 'name' length should be more then 7")
	String name;

	String description;

	@NotNull(message = "Product 'price' must not be null")
	@Min(value = 9, message = "Product 'price' should not be less than 9")
	@Positive(message = "Product 'price' should be positive")
	float price;

	@NotNull(message = "Product 'quantity' should not be null")
	@Min(value = 1, message = "Product 'quantity' min value is 0")
	Long quantity;

	@JsonProperty("unit_amount")
	@NotNull(message = "Product 'unit_amount' should not be null")
	@Min(value = 1, message = "Product 'unit_amount' should be more than 0")
	Long unitAmount;

	boolean shippable;

	@NotNull(message = "Product 'currency' should not be null")
	Currency currency;
}