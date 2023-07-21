package com.inikitagricenko.demo.stripe.handler.error;

public class BadRequestException extends RuntimeException {
	public BadRequestException(String message) {
		super(message);
	}
}
