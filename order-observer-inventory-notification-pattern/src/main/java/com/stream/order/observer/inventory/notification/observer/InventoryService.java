package com.stream.order.observer.inventory.notification.observer;

import com.stream.order.observer.inventory.notification.model.Order;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
//Create Observers
//Each observer will listen for specific events and react accordingly.
public class InventoryService {

    @EventListener
    public void handleOrderCreated(OrderCreatedEvent event) {
        Order order = event.getOrder();
        System.out.println("Updating inventory for product: " + order.getProductName());
        // Logic to reduce the inventory for the ordered product
    }
}
