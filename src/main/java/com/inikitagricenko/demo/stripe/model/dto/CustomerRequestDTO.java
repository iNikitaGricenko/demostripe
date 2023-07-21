package com.inikitagricenko.demo.stripe.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.io.Serializable;

@Data
public class CustomerRequestDTO implements Serializable {
    Long id;

    @Schema(example = "email@domain.com")
    @NotNull(message = "Customer 'email' is required")
    @Pattern(regexp = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$",
        message = "Customer 'email' should be email")
    private final String email;

    @Schema(example = "Nikit")
    @NotNull(message = "Customer 'first_name' is required")
    @JsonProperty("first_name")
    private final String firstName;

    @Schema(example = "Sambatist")
    @NotNull(message = "Customer 'second_name' is required")
    @JsonProperty("second_name")
    private final String secondName;

    @Schema(example = "+111 (202) 555-0125")
    @NotNull(message = "Customer 'phone' is required")
    @Pattern(regexp = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$"
      + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$"
      + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$",
        message = "Customer 'phone' should be phone number")
    private final String phone;
}
