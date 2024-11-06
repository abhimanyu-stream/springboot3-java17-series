package com.stream.order.service.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stream.order.service.model.Order;
import com.stream.order.service.model.OrderOutBoxEvent;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
@Component
public class OrderToOrderOutBoxEvent {

    @SneakyThrows
    public OrderOutBoxEvent map(Order order) {
        return
                OrderOutBoxEvent.builder()
                .aggregateId(order.getId().toString())
                .payload(new ObjectMapper().writeValueAsString(order))
                .createdAt(LocalDateTime.now())
                .processed(false)
                .build();
    }
}
