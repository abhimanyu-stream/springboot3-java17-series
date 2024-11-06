package com.stream.cqrs.order.command.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class OrderDto {

    private String orderId;
    @NotBlank
    @NotNull
    private String customerId;
    private String createdDate;
    private String updatedDate;
    @Min(1)
    private double orderAmount;
    private String status;
    private String invoiceNumber;
    private String transactionId;
    private String createdBy;
    private String updatedBy;
    @NotNull
    private List<Product> products = new ArrayList<>();
    @NotNull
    private ShippingAddress shippingAddress;
    @NotNull
    private BillingAddress billingAddress;

}
