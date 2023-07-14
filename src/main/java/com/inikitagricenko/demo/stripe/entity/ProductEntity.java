package com.inikitagricenko.demo.stripe.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "stripe_reference")
		private String stripeReference;

}
