package com.inikitagricenko.demo.stripe.controller;


import com.inikitagricenko.demo.stripe.adapter.ProductInputAdapter;
import com.inikitagricenko.demo.stripe.adapter.ProductOutputAdapter;
import com.inikitagricenko.demo.stripe.controller.interfaces.ProductEndpoint;
import com.inikitagricenko.demo.stripe.mapper.ProductMapper;
import com.inikitagricenko.demo.stripe.model.Product;
import com.inikitagricenko.demo.stripe.model.dto.ProductRequestDTO;
import com.inikitagricenko.demo.stripe.model.dto.ProductResponseDTO;
import com.inikitagricenko.demo.stripe.wrapper.ProductPage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController implements ProductEndpoint {

	private final ProductInputAdapter productInputAdapter;
	private final ProductOutputAdapter productOutputAdapter;
	private final ProductMapper productMapper;

	@Override
	@GetMapping
	public ProductPage retrieveAllProducts(Pageable pageable) {
		Page<Product> products = productOutputAdapter.retrieveAll(pageable);
		return new ProductPage(products.map(productMapper::toResponse));
	}

	@Override
	@GetMapping("/{id}")
	public ProductResponseDTO retrieveOrder(@PathVariable("id") Long id) {
		Product retrieved = productOutputAdapter.retrieve(id);
		return productMapper.toResponse(retrieved);
	}

	@Override
	@PostMapping
	public long addProduct(ProductRequestDTO requestDTO) {
		Product product = productMapper.toProduct(requestDTO);
		return productInputAdapter.add(product);
	}

}
