package com.inikitagricenko.demo.stripe.handler.error;

public class InvalidPaymentMethod extends BadRequestException {
	public InvalidPaymentMethod(String message) {
		super(message);
	}
}
