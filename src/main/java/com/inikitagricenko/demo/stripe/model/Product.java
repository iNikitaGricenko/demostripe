package com.inikitagricenko.demo.stripe.model;

import com.inikitagricenko.demo.stripe.model.enums.Currency;
import lombok.Data;

@Data
public class Product {

	private Long id;

	private String stripeReference;

	private String name;

	private String description;

	private Long price;

	private Long quantity;

	private Long unityAmount;

	private Currency currency;

}
