package com.faceit.demostripe.controller;

import com.faceit.demostripe.model.ChargeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/checkout")
@RequiredArgsConstructor
public class CheckoutController {

    public static final int FIFTY_DOLLARS = 50 * 100;
    @Value("${STRIPE_PUBLIC_KEY}")
    private String stripePublicKey;

    @GetMapping
    public String getCheckoutPage(Model model) {
        model.addAttribute("amount", FIFTY_DOLLARS);
        model.addAttribute("stripePublicKey", stripePublicKey);
        model.addAttribute("currency", ChargeRequest.Currency.USD);

        return "checkout";
    }

}
