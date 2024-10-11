package com.stream.cqrs.order.command.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class BillingAddress {
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    @Min(100000)
    private long zipCode;
    @NotBlank
    @NotNull
    private String country;
}
