package com.inikitagricenko.demo.stripe.controller;

import com.inikitagricenko.demo.stripe.model.dto.AnalyticsResponse;
import com.inikitagricenko.demo.stripe.model.dto.AnalyticsSearch;
import com.inikitagricenko.demo.stripe.model.dto.CustomerOrderRequestDTO;
import com.inikitagricenko.demo.stripe.model.dto.CustomerOrderResponseDTO;
import com.inikitagricenko.demo.stripe.model.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/order")
@Tag(name = "Order API")
@RequiredArgsConstructor
public class OrderRestController {

    @GetMapping
    @PageableAsQueryParam
    public List<CustomerOrderResponseDTO> retrieveAllOrders() {
        return new ArrayList<>();
    }

    @GetMapping("/{id}")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = CustomerOrderResponseDTO.class)))
    public CustomerOrderResponseDTO retrieveOrder(@PathVariable("id") Long id) {
        return null;
    }

    @GetMapping("/analytics")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = AnalyticsResponse.class)))
    public AnalyticsResponse getAnalytics(@ModelAttribute @Valid AnalyticsSearch analyticsSearchDTO) {
        return null;
    }

    @PostMapping
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = CustomerOrderResponseDTO.class)))
    public CustomerOrderResponseDTO addOrder(@Valid @RequestBody CustomerOrderRequestDTO requestDTO) {
        return null;
    }

    @PutMapping("/status/{id}")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = CustomerOrderResponseDTO.class)))
    public CustomerOrderResponseDTO updateOrderStatus(@PathVariable Long id, @RequestParam("status") OrderStatus status) {
        return null;
    }
}
