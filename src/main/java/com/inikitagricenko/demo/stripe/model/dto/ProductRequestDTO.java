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

	@NotNull(message = "Name should not be null")
	@NotEmpty(message = "Name should not be empty")
	@NotBlank(message = "Name should not be blank")
	@Length(min = 8, message = "Name length should be more then 7")
	String name;

	String description;

	@NotNull(message = "Price must not be null")
	@Min(value = 9, message = "Price should not be less than 9")
	@Positive(message = "Price should be positive")
	float price;

	@NotNull(message = "Quantity should not be null")
	@Min(value = 1, message = "Quantity min value is 0")
	Long quantity;

	@JsonProperty("unit_amount")
	@NotNull(message = "unit_amount should not be null")
	@Min(value = 1, message = "unit_amount should be more than 0")
	Long unitAmount;

	boolean shippable;

	@NotNull(message = "currency should not be null")
	Currency currency;
}