package com.stream.order.service.util;

import com.stream.order.service.model.Order;
import com.stream.order.service.transfer.OrderRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class OrderRequestToOrder {

    public Order map(OrderRequest orderRequest) {
        return  Order.builder()
                .customerId(orderRequest.getCustomerId())
                .name(orderRequest.getName())
                .productType(orderRequest.getProductType())
                .quantity(orderRequest.getQuantity())
                .price(orderRequest.getPrice())
                .orderDateTime(LocalDateTime.now())
                .build();
    }
}
