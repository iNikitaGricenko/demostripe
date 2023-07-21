package com.inikitagricenko.demo.stripe.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Value;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@Value
public class Payment implements Serializable {

	@NotNull(message = "Payment 'card_number' is required")
	@NotEmpty(message = "Payment 'card_number' should not be empty")
	@Pattern(regexp = "[0-9]+", message = "Payment 'card_number' should contain only numbers")
	@CreditCardNumber(message = "Payment 'card_number' should be credit card number")
	@JsonProperty("card_number")
	String cardNumber;

	@NotNull(message = "Payment 'expiration_month' is required")
	@NotEmpty(message = "Payment 'expiration_month' should not be empty")
	@Pattern(regexp = "[0-9]+", message = "Payment 'expiration_month' should contain only numbers")
	@Length(min = 1, max = 2, message = "Payment 'expiration_month' length minimum characters is 1 and maximum is 2")
	@JsonProperty("expiration_month")
	String expirationMonth;

	@NotNull(message = "Payment 'expiration_year' is required")
	@NotEmpty(message = "Payment 'expiration_year' should not be empty")
	@Length(min = 4, message = "Payment 'expiration_year' length minimum characters is 4")
	@Pattern(regexp = "[0-9]+", message = "Payment 'expiration_year' should contain only numbers")
	@JsonProperty("expiration_year")
	String expirationYear;

	@NotNull(message = "Payment 'cvc' is required")
	@NotEmpty(message = "Payment 'cvc' should not be empty")
	@Length(min = 3, max = 4, message = "Payment 'cvc' length minimum characters is 3 and maximum is 4")
	@Pattern(regexp = "[0-9]+", message = "Payment 'cvc' should contain only numbers")
	@JsonProperty("cvc")
	String cvc;

}
