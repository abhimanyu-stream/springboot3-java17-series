package com.stream.order.observer.inventory.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrderObserverInventoryNotificationApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderObserverInventoryNotificationApplication.class, args);
	}

}
/***
 * The Observer pattern is useful for handling various events in an e-commerce system, such as order creation, inventory updates, and email notifications.
 * this is ApplicationEventPublisher and  @EventListener implementation
 * this can be implemented using kafka event driven
 *
 *
 *
 * To test this setup, start the application and make a POST request to /api/orders with order details. This should:
 *
 * Save the order to the database.
 * Publish an OrderCreatedEvent.
 * Trigger the observers (InventoryService and NotificationService), which will update the inventory and send a notification.
 * Explanation of Key Concepts
 * Event-Driven Design: The application uses an event-driven design where placing an order triggers various actions (updating inventory, sending notifications).
 * Observer Pattern: InventoryService and NotificationService act as observers, listening for OrderCreatedEvent and reacting to it.
 * Decoupling: The services are decoupled, allowing new services to be added easily (e.g., analytics) without modifying OrderService.
 *
 *
 *
 * */
