package com.inikitagricenko.demo.stripe.service;

import com.inikitagricenko.demo.stripe.config.annotations.PerformanceMonitor;
import com.inikitagricenko.demo.stripe.model.Product;
import com.inikitagricenko.demo.stripe.persistence.ProductPersistence;
import com.inikitagricenko.demo.stripe.service.interfaces.IProductService;
import com.inikitagricenko.demo.stripe.service.stripe.StripeProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

	private final StripeProductService stripeProductService;
	private final ProductPersistence productPersistence;

	@Override
	@PerformanceMonitor
	public long add(Product product) {
		String reference = stripeProductService.addProduct(product);
		product.setStripeReference(reference);
		return productPersistence.save(product);
	}

	@Override
	@PerformanceMonitor
	public void delete(long productId) {
		Product retrieved = retrieve(productId);
		stripeProductService.delete(retrieved.getStripeReference());
		productPersistence.delete(productId);
	}

	@Override
	@PerformanceMonitor
	public Product retrieve(long productId) {
		return productPersistence.findById(productId);
	}

	@Override
	@PerformanceMonitor
	public List<Product> retrieveAll() {
		return productPersistence.findAll();
	}

	@Override
	public List<Product> retrieveAll(List<Long> productIds) {
		return productPersistence.findAll(productIds);
	}
}
