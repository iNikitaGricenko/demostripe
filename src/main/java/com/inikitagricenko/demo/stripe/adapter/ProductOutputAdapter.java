package com.inikitagricenko.demo.stripe.adapter;

import com.inikitagricenko.demo.stripe.model.Product;

import java.util.List;

public interface ProductOutputAdapter {

	Product retrieve(long productId);

	List<Product> retrieveAll();

	List<Product> retrieveAll(List<Long> productIds);

}
