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

	@NotNull(message = "")
	@NotEmpty
	@Pattern(regexp = "[0-9]+")
	@CreditCardNumber
	@JsonProperty("card_number")
	String cardNumber;

	@NotNull
	@NotEmpty
	@Pattern(regexp = "[0-9]+")
	@Length(min = 1, max = 2)
	@JsonProperty("expiration_month")
	String expirationMonth;

	@NotNull
	@NotEmpty
	@Length(min = 4)
	@Pattern(regexp = "[0-9]+")
	@JsonProperty("expiration_year")
	String expirationYear;

	@NotNull
	@NotEmpty
	@Length(min = 3, max = 4)
	@Pattern(regexp = "[0-9]+")
	@JsonProperty("cvc")
	String cvc;

}
