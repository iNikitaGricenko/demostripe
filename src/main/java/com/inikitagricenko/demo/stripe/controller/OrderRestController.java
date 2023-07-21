package com.inikitagricenko.demo.stripe.controller;

import com.inikitagricenko.demo.stripe.adapter.CustomerOrderInputAdapter;
import com.inikitagricenko.demo.stripe.adapter.CustomerOrderOutputAdapter;
import com.inikitagricenko.demo.stripe.mapper.CustomerOrderMapper;
import com.inikitagricenko.demo.stripe.model.CustomerOrder;
import com.inikitagricenko.demo.stripe.model.dto.*;
import com.inikitagricenko.demo.stripe.model.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/order")
@Tag(name = "Order API")
@RequiredArgsConstructor
public class OrderRestController {

    private final CustomerOrderInputAdapter customerOrderInputAdapter;
    private final CustomerOrderOutputAdapter customerOrderOutputAdapter;
    private final CustomerOrderMapper customerOrderMapper;
    private final Validator validator;

    @GetMapping
    public @ResponseBody List<CustomerOrderResponseDTO> retrieveAllOrders() {
        return customerOrderMapper.toResponse(customerOrderOutputAdapter.retrieveAll());
    }

    @GetMapping("/{id}")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = CustomerOrderResponseDTO.class)))
    public @ResponseBody CustomerOrderResponseDTO retrieveOrder(@PathVariable("id") Long id) {
        return customerOrderMapper.toResponse(customerOrderOutputAdapter.retrieve(id));
    }

    @GetMapping("/analytics")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = AnalyticsResponse.class)))
    public @ResponseBody AnalyticsResponse getAnalytics(@ModelAttribute @Valid AnalyticsSearch analyticsSearchDTO) {
        return customerOrderOutputAdapter.getAnalytics(analyticsSearchDTO);
    }

    @PostMapping
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Long.class)))
    public long payOrder(@Valid @RequestBody CustomerOrderRequestDTO requestDTO) {
        CustomerRequestDTO customer = requestDTO.getCustomer();
        if (customer.getId() == null) {
            validateCustomer(customer);
        }
        CustomerOrder order = customerOrderMapper.toOrder(requestDTO);
        return customerOrderInputAdapter.pay(order);
    }

    @PostMapping("/{id}")
    @ApiResponse(responseCode = "200")
    public void confirmPay(@PathVariable Long id) {
        customerOrderInputAdapter.confirmPay(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "200")
    public void cancelPay(@PathVariable Long id) {
        customerOrderInputAdapter.cancelPay(id);
    }

    @PutMapping("/delivery/{id}")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Long.class)))
    public long updateOrderDeliveryStatus(@PathVariable Long id, @RequestParam("status") OrderStatus status) {
        return customerOrderInputAdapter.updateDeliveryStatus(id, status);
    }

    private void validateCustomer(CustomerRequestDTO customer) {
        Set<ConstraintViolation<CustomerRequestDTO>> emailViolations =
            validator.validateProperty(customer, "email");
        Set<ConstraintViolation<CustomerRequestDTO>> phoneViolations =
            validator.validateProperty(customer, "phone");

        if (!emailViolations.isEmpty() && !phoneViolations.isEmpty()) {
            throw new ValidationException("Customer id or email or phone should contains in request");
        }
    }
}
