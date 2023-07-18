package com.inikitagricenko.demo.stripe.model;

import com.inikitagricenko.demo.stripe.model.enums.Currency;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Subscription {

	@Id
	private Long id;

	private String stripeReference;

	private String name;

	private LocalDateTime cancelAt;

	private LocalDateTime created;

	private Currency currency;

	private Customer customer;

	private String description;

	private Long discount;

	private List<Product> productList;

	private String status;

}
