package com.faceit.demostripe.controller;

import com.faceit.demostripe.model.ChargeRequest;
import com.faceit.demostripe.model.ChargeResult;
import com.faceit.demostripe.service.CheckoutService;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

import static com.faceit.demostripe.model.ChargeRequest.Currency.*;

@RestController
@RequestMapping("/api/v1/charge")
@RequiredArgsConstructor
public class ChargeController {

    private final CheckoutService checkoutService;

    @PostMapping
    public ChargeResult charge(ChargeRequest chargeRequest) throws StripeException, AuthenticationException {
        chargeRequest.setDescription("Test charge description");
        chargeRequest.setCurrency(USD);

        Charge charge = checkoutService.charge(chargeRequest);

        ChargeResult chargeResult = new ChargeResult();
        chargeResult.setId(charge.getId());
        chargeResult.setChargeId(charge.getId());
        chargeResult.setStatus(charge.getStatus());
        chargeResult.setBalanceTransaction(charge.getBalanceTransaction());

        return chargeResult;
    }

    @ExceptionHandler(StripeException.class)
    public ResponseEntity<String> handleError(StripeException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

}
