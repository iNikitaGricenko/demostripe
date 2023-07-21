package com.inikitagricenko.demo.stripe.controller.interfaces;

import com.inikitagricenko.demo.stripe.handler.error.ErrorBody;
import com.inikitagricenko.demo.stripe.handler.error.ValidationErrorBody;
import com.inikitagricenko.demo.stripe.model.dto.SubscriptionCreateDTO;
import com.inikitagricenko.demo.stripe.model.dto.SubscriptionResponseDTO;
import com.inikitagricenko.demo.stripe.wrapper.SubscriptionPage;
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

@Tag(name = "Subscription API")
public interface SubscriptionEndpoint {
	@GetMapping
	@PageableAsQueryParam
	@ApiResponse(
			responseCode = "200",
			content = @Content(mediaType = "application/json", array = @ArraySchema(
					schema = @Schema(implementation = SubscriptionPage.class))),
			description = "Retrieve all subscriptions")
	@ResponseBody
	SubscriptionPage retrieveSubscriptions(Pageable pageable);

	@GetMapping("/{subscriptionId}")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					content = @Content(mediaType = "application/json", schema = @Schema(
							implementation = SubscriptionResponseDTO.class)),
					description = "Retrieve subscription by id"),
			@ApiResponse(
					responseCode = "403",
					content = @Content(mediaType = "application/json", schema = @Schema(
							implementation = ErrorBody.class)),
					description = "Subscription not found")
	})
	@ResponseBody SubscriptionResponseDTO retrieveSubscription(@PathVariable Long subscriptionId);

	@PostMapping
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					content = @Content(schema = @Schema(
							implementation = Long.class)),
					description = "Create subscription, returns id"),
			@ApiResponse(
					responseCode = "400",
					content = @Content(mediaType = "application/json", schema = @Schema(
							implementation = ValidationErrorBody.class)),
					description = "Subscription body is invalid")
	})
	long addSubscription(@Valid @RequestBody SubscriptionCreateDTO requestDTO);

	@PostMapping("/subscribe/{subscriptionId}/{customerId}")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					content = @Content(schema = @Schema(
							implementation = Long.class)),
					description = "Subscribe customer, returns id"),
			@ApiResponse(
					responseCode = "403",
					content = @Content(mediaType = "application/json", schema = @Schema(
							implementation = ErrorBody.class)),
					description = "Subscription not found"),
			@ApiResponse(
					responseCode = "403",
					content = @Content(mediaType = "application/json", schema = @Schema(
							implementation = ErrorBody.class)),
					description = "Customer not found")
	})
	long subscribeCustomer(@PathVariable("subscriptionId") long subscriptionId, @PathVariable("customerId") long customerId);

	@PostMapping("/{subscriptionId}")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Resume subscription"),
			@ApiResponse(
					responseCode = "403",
					content = @Content(mediaType = "application/json", schema = @Schema(
							implementation = ErrorBody.class)),
					description = "Subscription not found")
	})
	void resumeSubscription(@PathVariable Long subscriptionId);

	@DeleteMapping("/{subscriptionId}")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Cancel subscription"),
			@ApiResponse(
					responseCode = "403",
					content = @Content(mediaType = "application/json", schema = @Schema(
							implementation = ErrorBody.class)),
					description = "Subscription not found")
	})
	void cancelSubscription(@PathVariable Long subscriptionId);
}
