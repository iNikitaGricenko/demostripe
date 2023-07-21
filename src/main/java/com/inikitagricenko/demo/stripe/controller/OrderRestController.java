package com.inikitagricenko.demo.stripe.controller;

import com.inikitagricenko.demo.stripe.adapter.CustomerOrderInputAdapter;
import com.inikitagricenko.demo.stripe.adapter.CustomerOrderOutputAdapter;
import com.inikitagricenko.demo.stripe.controller.interfaces.OrderEndpoint;
import com.inikitagricenko.demo.stripe.mapper.CustomerOrderMapper;
import com.inikitagricenko.demo.stripe.model.CustomerOrder;
import com.inikitagricenko.demo.stripe.model.dto.*;
import com.inikitagricenko.demo.stripe.model.enums.OrderStatus;
import com.inikitagricenko.demo.stripe.wrapper.OrderPage;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderRestController implements OrderEndpoint {

    private final CustomerOrderInputAdapter customerOrderInputAdapter;
    private final CustomerOrderOutputAdapter customerOrderOutputAdapter;
    private final CustomerOrderMapper customerOrderMapper;
    private final Validator validator;

    @Override
    @GetMapping
    public OrderPage retrieveAllOrders(Pageable pageable) {
        return new OrderPage(customerOrderOutputAdapter.retrieveAll(pageable).map(customerOrderMapper::toResponse));
    }

    @Override
    @GetMapping("/{id}")
    public CustomerOrderResponseDTO retrieveOrder(@PathVariable("id") Long id) {
        return customerOrderMapper.toResponse(customerOrderOutputAdapter.retrieve(id));
    }

    @Override
    @GetMapping("/analytics")
    public AnalyticsResponse getAnalytics(AnalyticsSearch analyticsSearchDTO) {
        return customerOrderOutputAdapter.getAnalytics(analyticsSearchDTO);
    }

    @Override
    @PostMapping
    public long payOrder(CustomerOrderRequestDTO requestDTO) {
        CustomerRequestDTO customer = requestDTO.getCustomer();
        if (customer.getId() == null) {
            validateCustomer(customer);
        }
        CustomerOrder order = customerOrderMapper.toOrder(requestDTO);
        return customerOrderInputAdapter.pay(order);
    }

    @Override
    @PostMapping("/{id}")
    public void confirmPay(@PathVariable Long id) {
        customerOrderInputAdapter.confirmPay(id);
    }

    @Override
    @DeleteMapping("/{id}")
    public void cancelPay(@PathVariable Long id) {
        customerOrderInputAdapter.cancelPay(id);
    }

    @Override
    @PutMapping("/delivery/{id}")
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
