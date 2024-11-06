package com.stream.order_query_service.dto;

import lombok.Data;

import java.util.List;

/**
 * @author vishnu.g
 */
@Data
public class OrderDto {

    private String orderId;
    private String customerId;
    private String createdDate;
    private String updatedDate;
    private double orderAmount;
    private String status;
    private String invoiceNumber;
    private String transactionId;
    private String createdBy;
    private String updatedBy;
    private List<Product> products;
    private ShippingAddress shippingAddress;
    private BillingAddress billingAddress;
}
