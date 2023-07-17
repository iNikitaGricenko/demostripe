package com.inikitagricenko.demo.stripe.adapter;

import com.inikitagricenko.demo.stripe.model.Product;

public interface ProductInputAdapter {

	long add(Product product);

	void delete(long productId);

}
