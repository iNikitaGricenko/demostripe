package com.inikitagricenko.demo.stripe.controller.interfaces;

import com.inikitagricenko.demo.stripe.handler.error.ErrorBody;
import com.inikitagricenko.demo.stripe.handler.error.ValidationErrorBody;
import com.inikitagricenko.demo.stripe.model.dto.AnalyticsResponse;
import com.inikitagricenko.demo.stripe.model.dto.AnalyticsSearch;
import com.inikitagricenko.demo.stripe.model.dto.CustomerOrderRequestDTO;
import com.inikitagricenko.demo.stripe.model.dto.CustomerOrderResponseDTO;
import com.inikitagricenko.demo.stripe.model.enums.OrderStatus;
import com.inikitagricenko.demo.stripe.wrapper.OrderPage;
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

@Tag(name = "Order API")
public interface OrderEndpoint {
	@GetMapping
	@PageableAsQueryParam
	@ApiResponse(
			responseCode = "200",
			content = @Content(mediaType = "application/json", array = @ArraySchema(
					schema = @Schema(implementation = OrderPage.class))),
			description = "Retrieve all orders")
	@ResponseBody
	OrderPage retrieveAllOrders(Pageable pageable);

	@GetMapping("/{id}")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					content = @Content(mediaType = "application/json", schema = @Schema(
							implementation = CustomerOrderResponseDTO.class)),
					description = "Retrieve order by order id"),
			@ApiResponse(
					responseCode = "403",
					content = @Content(mediaType = "application/json", schema = @Schema(
							implementation = ErrorBody.class)),
					description = "Order not found")
	})
	@ResponseBody CustomerOrderResponseDTO retrieveOrder(@PathVariable("id") Long id);

	@GetMapping("/analytics")
	@ApiResponse(
			responseCode = "200",
			content = @Content(mediaType = "application/json", schema = @Schema(
					implementation = AnalyticsResponse.class)),
			description = "Get analytics of order by date range and order status")
	@ResponseBody AnalyticsResponse getAnalytics(@ModelAttribute @Valid AnalyticsSearch analyticsSearchDTO);

	@PostMapping
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					content = @Content(schema = @Schema(
							implementation = Long.class)),
					description = "Create order. Pay order. Returns order id"),
			@ApiResponse(
					responseCode = "400",
					content = @Content(mediaType = "application/json", schema = @Schema(
							implementation = ErrorBody.class)),
					description = "Order items invalid"),
			@ApiResponse(
					responseCode = "400",
					content = @Content(mediaType = "application/json", schema = @Schema(
							implementation = ValidationErrorBody.class)),
					description = "Order body is invalid")
	})
	long payOrder(@Valid @RequestBody CustomerOrderRequestDTO requestDTO);

	@PostMapping("/{id}")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Confirm payment"),
			@ApiResponse(
					responseCode = "403",
					content = @Content(mediaType = "application/json", schema = @Schema(
							implementation = ErrorBody.class)),
					description = "Order by id not found")
	})
	void confirmPay(@PathVariable Long id);

	@DeleteMapping("/{id}")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Cancel payment"),
			@ApiResponse(
					responseCode = "403",
					content = @Content(mediaType = "application/json", schema = @Schema(
							implementation = ErrorBody.class)),
					description = "Order by id not found")
	})
	void cancelPay(@PathVariable Long id);

	@PutMapping("/delivery/{id}")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					content = @Content(schema = @Schema(
							implementation = Long.class)),
					description = "Update delivery status of order"),
			@ApiResponse(
					responseCode = "400",
					content = @Content(mediaType = "application/json", schema = @Schema(
							implementation = ErrorBody.class)),
					description = "Order already have status of refund"),
			@ApiResponse(
					responseCode = "403",
					content = @Content(mediaType = "application/json", schema = @Schema(
							implementation = ErrorBody.class)),
					description = "Order by id not found")
	})
	long updateOrderDeliveryStatus(@PathVariable Long id, @RequestParam("status") OrderStatus status);
}
