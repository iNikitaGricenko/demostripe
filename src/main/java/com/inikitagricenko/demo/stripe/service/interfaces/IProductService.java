package com.inikitagricenko.demo.stripe.service.interfaces;

import com.inikitagricenko.demo.stripe.adapter.ProductInputAdapter;
import com.inikitagricenko.demo.stripe.adapter.ProductOutputAdapter;
import com.inikitagricenko.demo.stripe.config.annotations.PerformanceMonitor;
import com.inikitagricenko.demo.stripe.model.Product;

import java.util.List;

public interface IProductService extends ProductInputAdapter, ProductOutputAdapter {
	void reduceQuantity(long productId, long quantity);

	void increaseQuantity(long productId, long quantity);

	boolean exists(List<Long> productIds);
}
