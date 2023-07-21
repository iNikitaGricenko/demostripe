package com.inikitagricenko.demo.stripe.adapter;

import com.inikitagricenko.demo.stripe.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductOutputAdapter {

	Product retrieve(long productId);

	Page<Product> retrieveAll(Pageable pageable);

	List<Product> retrieveAll(List<Long> productIds);

}
