package com.inikitagricenko.demo.stripe.model.dto;

import com.inikitagricenko.demo.stripe.model.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;

public record AnalyticsSearch(
    @Schema(example = "INPROGRESS") OrderStatus status,

    @Schema(example = "2007-12-03", description = "years-month-days")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,

    @Schema(example = "2022-09-26", description = "years-month-days")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) implements Serializable {

}