package com.inikitagricenko.demo.stripe.controller;

import com.inikitagricenko.demo.stripe.model.AnalyticsResponse;
import com.inikitagricenko.demo.stripe.model.AnalyticsSearch;
import com.inikitagricenko.demo.stripe.model.dto.CustomerOrderRequestDTO;
import com.inikitagricenko.demo.stripe.model.dto.CustomerOrderResponseDTO;
import com.inikitagricenko.demo.stripe.model.enums.OrderStatus;
import com.inikitagricenko.demo.stripe.wrapper.RestPage;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@Tag(name = "Order API")
@RequiredArgsConstructor
public class OrderRestController {

    @GetMapping
    @PageableAsQueryParam
    public Page<CustomerOrderResponseDTO> getAll(Pageable pageable) {
        return new RestPage<>(Page.empty());
    }

    @GetMapping("/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(
                    implementation = CustomerOrderResponseDTO.class)))})
    public CustomerOrderResponseDTO getOne(@PathVariable("id") Long id) {
        return null;
    }

    @GetMapping("/analytics")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(
                    implementation = AnalyticsResponse.class)))})
    public AnalyticsResponse getAnalytics(@ModelAttribute @Valid AnalyticsSearch analyticsSearchDTO) {
        return null;
    }

    @PostMapping
    @ApiResponse(responseCode = "200", content = @Content(
            schema = @Schema(implementation = CustomerOrderResponseDTO.class)))
    public CustomerOrderResponseDTO add(@Valid @RequestBody CustomerOrderRequestDTO requestDTO) {
        return null;
    }

    @PutMapping("/{id}")
    @ApiResponse(responseCode = "200", content = @Content(
            schema = @Schema(implementation = CustomerOrderResponseDTO.class)))
    public CustomerOrderResponseDTO update(@PathVariable Long id, @RequestParam("status") OrderStatus status) {
        return null;
    }
}
