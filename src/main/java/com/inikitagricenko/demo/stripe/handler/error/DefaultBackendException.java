package com.inikitagricenko.demo.stripe.handler.error;

public class DefaultBackendException extends RuntimeException {
	public DefaultBackendException(Throwable cause) {
		super("Something went wrong", cause);
	}

}
