package com.stream.cqrs.order.command.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
@Entity(name = "orders")
public class Order {

    @Id
    @Column(name = "order_id")
    private String orderId;

    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "created_date")
    private String createdDate;

    @Column(name = "updated_date")
    private String updatedDate;

    @Column(name = "order_amount")
    private double orderAmount;

    @Column(name = "status")
    private String status;

    @Column(name = "invoice_number")
    private String invoiceNumber;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @Embedded
    private BillingAddress billingAddress;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_fid", referencedColumnName = "order_id")
    private List<Product> products = new ArrayList<>();

    @Column(name = "transaction_id")
    private String transactionId;

    @Embedded
    private ShippingAddress shippingAddress;

}
