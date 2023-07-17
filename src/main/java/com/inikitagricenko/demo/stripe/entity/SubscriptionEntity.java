package com.inikitagricenko.demo.stripe.entity;

import com.inikitagricenko.demo.stripe.model.enums.Currency;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@RequiredArgsConstructor
@SQLDelete(sql = "UPDATE subscription e " +
        "SET deleted=true, deleted_at=now() " +
        "WHERE e.subscription_id=?")
@Entity(name = "subscription")
public class SubscriptionEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "subscription_id")
	private Long id;

	@Column(name = "stripe_reference")
	private String stripeReference;

	@Column(name = "name")
	private String name;

	@Column(name = "cancel_at")
	private LocalDateTime cancelAt;

	@Column(name = "created")
	private LocalDateTime created = LocalDateTime.now();

	@Enumerated
	@Column(name = "currency")
	private Currency currency;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private CustomerEntity customer;

	@Column(name = "description")
	private String description;

	@Column(name = "discount")
	private Long discount;

	@ManyToMany
	@JoinColumn(name = "product_id")
	private List<ProductEntity> itemList = new ArrayList<>();

	@Column(name = "status")
	private String status;

	@JoinColumn(name = "delete_at")
	private LocalDateTime deleteAt;

	@JoinColumn(name = "deleted")
	private boolean deleted = false;

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
			return false;
		}
		SubscriptionEntity that = (SubscriptionEntity) o;
		return getId() != null && Objects.equals(getId(), that.getId());
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}

	@Override
	public String toString() {
		return "Subscription %s (%s) status %s. Discount %s".formatted(name, description, status, discount);
	}
}
