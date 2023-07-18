package com.inikitagricenko.demo.stripe.handler.error;

public class InvalidPaymentMethod extends RuntimeException {
	public InvalidPaymentMethod(String message) {
		super(message);
	}
}
