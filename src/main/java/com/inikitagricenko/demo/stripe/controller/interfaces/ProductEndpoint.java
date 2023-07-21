package com.inikitagricenko.demo.stripe.controller.interfaces;

import com.inikitagricenko.demo.stripe.handler.error.ErrorBody;
import com.inikitagricenko.demo.stripe.handler.error.ValidationErrorBody;
import com.inikitagricenko.demo.stripe.model.dto.ProductRequestDTO;
import com.inikitagricenko.demo.stripe.model.dto.ProductResponseDTO;
import com.inikitagricenko.demo.stripe.wrapper.ProductPage;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Product API")
public interface ProductEndpoint {
	@GetMapping
	@PageableAsQueryParam
	@ApiResponse
			(responseCode = "200",
					content = @Content(mediaType = "application/json", array = @ArraySchema(
							schema = @Schema(implementation = ProductPage.class))),
					description = "Retrieve all products")
	@ResponseBody
	ProductPage retrieveAllProducts(Pageable pageable);

	@GetMapping("/{id}")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					content = @Content(mediaType = "application/json", schema = @Schema(
							implementation = ProductResponseDTO.class)),
					description = "Retrieve product by id"),
			@ApiResponse(
					responseCode = "403",
					content = @Content(mediaType = "application/json", schema = @Schema(
							implementation = ErrorBody.class)),
					description = "Product not found")
	})
	@ResponseBody ProductResponseDTO retrieveOrder(@PathVariable("id") Long id);

	@PostMapping
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					content = @Content(schema = @Schema(
							implementation = Long.class)),
					description = "Create product, returns id"),
			@ApiResponse(
					responseCode = "400",
					content = @Content(mediaType = "application/json", schema = @Schema(
							implementation = ValidationErrorBody.class)),
					description = "Product body is invalid")
	})
	long addProduct(@Valid @RequestBody ProductRequestDTO requestDTO);
}
