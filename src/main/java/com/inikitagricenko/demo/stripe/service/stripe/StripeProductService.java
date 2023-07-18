package com.inikitagricenko.demo.stripe.service.stripe;

import com.inikitagricenko.demo.stripe.handler.error.DefaultBackendException;
import com.stripe.exception.StripeException;
import com.stripe.model.Product;
import com.stripe.param.ProductCreateParams;
import com.stripe.param.ProductListParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StripeProductService {

	public String addProduct(com.inikitagricenko.demo.stripe.model.Product product) {
		try {
			ProductCreateParams productCreateParams = ProductCreateParams.builder()
					.setName(product.getName())
					.setDescription(product.getDescription())
					.setType(ProductCreateParams.Type.GOOD)
					.setShippable(product.isShippable())
					.setActive(true)
					.build();

			return Product.create(productCreateParams).getId();
		} catch (StripeException e) {
			throw new DefaultBackendException(e);
		}
	}

	public Product retrieve(String productId) {
		try {
			return Product.retrieve(productId);
		} catch (StripeException e) {
			throw new DefaultBackendException(e);
		}
	}

	public List<Product> retrieveAll() {
		try {
			ProductListParams productListParams = ProductListParams.builder()
					.setLimit(10L)
					.build();

			return Product.list(productListParams).getData();
		} catch (StripeException e) {
			throw new DefaultBackendException(e);
		}
	}

	public void delete(String productId) {
		try {
			Product.retrieve(productId).delete();
		} catch (StripeException e) {
			throw new DefaultBackendException(e);
		}
	}

}
