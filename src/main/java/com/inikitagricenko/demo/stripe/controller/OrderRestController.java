package com.inikitagricenko.demo.stripe.controller;

import com.inikitagricenko.demo.stripe.adapter.CustomerOrderInputAdapter;
import com.inikitagricenko.demo.stripe.adapter.CustomerOrderOutputAdapter;
import com.inikitagricenko.demo.stripe.mapper.CustomerOrderMapper;
import com.inikitagricenko.demo.stripe.model.CustomerOrder;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@Tag(name = "Order API")
@RequiredArgsConstructor
public class OrderRestController {

    private final CustomerOrderInputAdapter customerOrderInputAdapter;
    private final CustomerOrderOutputAdapter customerOrderOutputAdapter;
    private final CustomerOrderMapper customerOrderMapper;

    @GetMapping
    public List<CustomerOrderResponseDTO> retrieveAllOrders() {
        return customerOrderMapper.toResponse(customerOrderOutputAdapter.retrieveAll());
    }

    @GetMapping("/{id}")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = CustomerOrderResponseDTO.class)))
    public CustomerOrderResponseDTO retrieveOrder(@PathVariable("id") Long id) {
        return customerOrderMapper.toResponse(customerOrderOutputAdapter.retrieve(id));
    }

    @GetMapping("/analytics")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = AnalyticsResponse.class)))
    public AnalyticsResponse getAnalytics(@ModelAttribute @Valid AnalyticsSearch analyticsSearchDTO) {
        return customerOrderOutputAdapter.getAnalytics(analyticsSearchDTO);
    }

    @PostMapping
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Long.class)))
    public long payOrder(@Valid @RequestBody CustomerOrderRequestDTO requestDTO) {
        CustomerOrder order = customerOrderMapper.toOrder(requestDTO);
        return customerOrderInputAdapter.pay(order);
    }

    @PostMapping("/{id}")
    @ApiResponse(responseCode = "200")
    public void confirmPay(@PathVariable Long id) {
        customerOrderInputAdapter.confirmPay(id);
    }

    @PutMapping("/delivery/{id}")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Long.class)))
    public long updateOrderDeliveryStatus(@PathVariable Long id, @RequestParam("status") OrderStatus status) {
        return customerOrderInputAdapter.updateDeliveryStatus(id, status);
    }
}
