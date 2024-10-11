package com.stream.cqrs.order.command.events;

import com.stream.cqrs.order.command.dto.BillingAddress;
import com.stream.cqrs.order.command.dto.Product;
import com.stream.cqrs.order.command.dto.ShippingAddress;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateEvent {
    private String orderId;
    private String customerId;
    private String createdDate;
    private String updatedDate;
    private double orderAmount;
    private String status;
    private String invoiceNumber;
    private String createdBy;
    private String updatedBy;
    private List<Product> products;
    private String transactionId;
    private ShippingAddress shippingAddress;
    private BillingAddress billingAddress;
}
