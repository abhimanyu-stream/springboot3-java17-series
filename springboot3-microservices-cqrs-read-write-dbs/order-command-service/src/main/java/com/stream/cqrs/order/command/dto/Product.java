package com.stream.cqrs.order.command.dto;

import lombok.Data;


@Data
public class Product {
    private String productId;
    private String productName;
    private int quantity;
    private double price;
}
