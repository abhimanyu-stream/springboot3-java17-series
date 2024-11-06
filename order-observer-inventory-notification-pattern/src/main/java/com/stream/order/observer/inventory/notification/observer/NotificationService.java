package com.stream.order.observer.inventory.notification.observer;

import com.stream.order.observer.inventory.notification.model.Order;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
//Create Observers
//Each observer will listen for specific events and react accordingly.
public class NotificationService {

    private final JavaMailSender mailSender;

    public NotificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @EventListener
    public void handleOrderCreated(OrderCreatedEvent event) {
        Order order = event.getOrder();
        System.out.println("Sending notification for order ID: " + order.getId());

        // Logic to send an email notification
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("customer@example.com");
        message.setSubject("Order Confirmation");
        message.setText("Your order for " + order.getProductName() + " has been placed successfully.");
        mailSender.send(message);
    }
}
