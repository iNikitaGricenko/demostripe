package com.inikitagricenko.demo.stripe.entity;

import com.inikitagricenko.demo.stripe.model.enums.Currency;
import com.inikitagricenko.demo.stripe.model.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@ToString
@Getter @Setter
@RequiredArgsConstructor
@SQLDelete(sql = "UPDATE customer_order e " +
        "SET deleted=true, deleted_at=now() " +
        "WHERE e.customer_order_id=?")
public class CustomerOrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_order_id")
    private Long id;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Enumerated
    @Column(name = "payment_currency")
    private Currency paymentCurrency = Currency.EUR;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "country")
    private String country;

    @Column(name = "zipCode")
    private String zipCode;

    @Column(name = "description")
    private String description;

    @Column(name = "created")
    private LocalDateTime created;

    @Enumerated
    @Column(name = "order_status")
    private OrderStatus status;

    @Column(name = "completed")
    private LocalDateTime completed;

    @ManyToOne(
            targetEntity = CustomerEntity.class,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    @ToString.Exclude
    private CustomerEntity customer;

    @Column(name = "deleted")
    private boolean isDeleted = false;

    @Column(name = "deleted_at", insertable = false)
    private LocalDateTime deletedAt;

    @ElementCollection
    @CollectionTable(name = "customer_order_items", joinColumns = {
            @JoinColumn(name = "customer_order_id", referencedColumnName = "customer_order_id")})
    private Set<OrderItemEntity> orderItems = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        CustomerOrderEntity that = (CustomerOrderEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
