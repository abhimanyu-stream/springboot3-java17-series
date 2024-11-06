package com.stream.cqrs.order.command.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderUpdateEvent {
    private String orderId;
    private String status;
    private String transactionId;
}
