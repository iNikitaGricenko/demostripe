package com.inikitagricenko.demo.stripe.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.inikitagricenko.demo.stripe.entity.ProductEntity;
import com.inikitagricenko.demo.stripe.model.enums.Currency;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link ProductEntity}
 */
@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductResponseDTO implements Serializable {
	Long id;
	String name;
	String description;
	Long price;
	Long quantity;
	Long unitAmount;
	boolean shippable;
	Currency currency;
	LocalDateTime addedAt;
}