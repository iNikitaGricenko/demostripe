package com.inikitagricenko.demo.stripe.model;

import com.inikitagricenko.demo.stripe.model.enums.Currency;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class Subscription {

	@Id
	private Long id;

	private String stripeReference;

	private String name;

	private LocalDateTime cancelAt;

	private LocalDateTime created;

	private Currency currency;

	private Set<Customer> customerList = new HashSet<>();

	private String description;

	private Long discount;

	private Set<Product> productList = new HashSet<>();

	private String status;

}
