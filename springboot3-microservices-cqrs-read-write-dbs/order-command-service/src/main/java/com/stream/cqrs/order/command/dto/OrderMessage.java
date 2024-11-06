package com.stream.cqrs.order.command.dto;

import lombok.Data;


@Data
public class OrderMessage {
    private String orderId;
    private Double orderAmount;
    private String orderDate;
    private String status;
}
