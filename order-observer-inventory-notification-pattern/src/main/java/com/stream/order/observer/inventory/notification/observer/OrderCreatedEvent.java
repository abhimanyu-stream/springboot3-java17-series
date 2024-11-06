package com.stream.order.observer.inventory.notification.observer;

import com.stream.order.observer.inventory.notification.model.Order;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@Builder
//Create Event Classes
//Define events for your domain, such as OrderCreatedEvent.
public class OrderCreatedEvent implements Serializable {

    private final Order order;

    public OrderCreatedEvent(Order order) {
        this.order = order;
    }
}
