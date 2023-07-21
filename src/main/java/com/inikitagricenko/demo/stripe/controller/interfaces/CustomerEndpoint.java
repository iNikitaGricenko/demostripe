package com.inikitagricenko.demo.stripe.controller.interfaces;

import com.inikitagricenko.demo.stripe.handler.error.ErrorBody;
import com.inikitagricenko.demo.stripe.handler.error.ValidationErrorBody;
import com.inikitagricenko.demo.stripe.model.dto.CustomerRequestDTO;
import com.inikitagricenko.demo.stripe.model.dto.CustomerResponseDTO;
import com.inikitagricenko.demo.stripe.wrapper.CustomerPage;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Customer API")
public interface CustomerEndpoint {
	@GetMapping
	@PageableAsQueryParam
	@ApiResponse(
			responseCode = "200",
			content = @Content(mediaType = "application/json", array = @ArraySchema(
					schema = @Schema(implementation = CustomerPage.class))),
			description = "Retrieve all customers")
	@ResponseBody
	CustomerPage retrieveAllCustomers(Pageable pageable);

	@PostMapping
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					content = @Content(schema = @Schema(implementation = Long.class)),
					description = "Create customer, returns id"),
			@ApiResponse(
					responseCode = "400",
					content = @Content(mediaType = "application/json", schema = @Schema(
							implementation = ValidationErrorBody.class)),
					description = "Customer body is invalid")
	})
	Long addCustomer(@RequestBody CustomerRequestDTO customerRequest);

	@DeleteMapping("/{customerId}")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Delete customer"),
			@ApiResponse(
					responseCode = "403",
					content = @Content(mediaType = "application/json", schema = @Schema(
							implementation = ErrorBody.class)),
					description = "Customer by id not found")
	})
	void deleteCustomer(@PathVariable Long customerId);

	@GetMapping("/{customerId}")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					content = @Content(mediaType = "application/json", schema = @Schema(
							implementation = CustomerResponseDTO.class)),
					description = "Retrieve customer by id"),
			@ApiResponse(
					responseCode = "403",
					content = @Content(mediaType = "application/json", schema = @Schema(
							implementation = ErrorBody.class)),
					description = "Customer not found")
	})
	@ResponseBody CustomerResponseDTO retrieveCustomer(@PathVariable Long customerId);
}
