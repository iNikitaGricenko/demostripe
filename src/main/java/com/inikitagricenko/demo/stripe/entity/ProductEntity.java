package com.inikitagricenko.demo.stripe.entity;

import com.inikitagricenko.demo.stripe.model.enums.Currency;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity(name = "product")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class ProductEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private Long id;

	@Column(name = "stripe_reference")
	private String stripeReference;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "price")
	private float price;

	@Column(name = "quantity")
	private Long quantity;

	@Column(name = "unit_amount")
	private Long unitAmount;

	@Column(name = "shippable")
	private boolean shippable = true;

	@Enumerated
	@Column(name = "currency")
	private Currency currency;

	@Column(name = "added_at")
	private LocalDateTime addedAt = LocalDateTime.now();

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
			return false;
		}
		ProductEntity that = (ProductEntity) o;
		return getId() != null && Objects.equals(getId(), that.getId());
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
