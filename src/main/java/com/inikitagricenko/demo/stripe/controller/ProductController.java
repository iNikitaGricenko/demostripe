package com.inikitagricenko.demo.stripe.controller;


import com.inikitagricenko.demo.stripe.adapter.ProductInputAdapter;
import com.inikitagricenko.demo.stripe.adapter.ProductOutputAdapter;
import com.inikitagricenko.demo.stripe.model.dto.ProductRequestDTO;
import com.inikitagricenko.demo.stripe.mapper.ProductMapper;
import com.inikitagricenko.demo.stripe.model.Product;
import com.inikitagricenko.demo.stripe.model.dto.ProductResponseDTO;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@Tag(name = "Product API")
@RequiredArgsConstructor
public class ProductController {

	private final ProductInputAdapter productInputAdapter;
	private final ProductOutputAdapter productOutputAdapter;
	private final ProductMapper productMapper;

	@GetMapping
	public @ResponseBody List<ProductResponseDTO> retrieveAllProducts() {
		List<Product> products = productOutputAdapter.retrieveAll();
		return productMapper.toResponses(products);
	}

	@GetMapping("/{id}")
	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = ProductResponseDTO.class)))
	public @ResponseBody ProductResponseDTO retrieveOrder(@PathVariable("id") Long id) {
		Product retrieved = productOutputAdapter.retrieve(id);
		return productMapper.toResponse(retrieved);
	}

	@PostMapping
	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = ProductRequestDTO.class)))
	public long addProduct(@Valid @RequestBody ProductRequestDTO requestDTO) {
		Product product = productMapper.toProduct(requestDTO);
		return productInputAdapter.add(product);
	}

}
