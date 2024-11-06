package com.stream.message.strategy.strategy;

/***
 * The Strategy Pattern to implement a message-sending system with multiple strategies for sending messages through different channels like Email, SMS, and WhatsApp. Each message strategy is encapsulated in its own class, making it easy to add, modify, or replace strategies without changing the core application logic.
 *
 * Scenario
 * Imagine a notification system in a Spring Boot application that needs to send messages through various channels (Email, SMS, and WhatsApp). By using the Strategy Pattern, we can encapsulate each messaging method in its own class and dynamically select which messaging strategy to use based on user preference or specific criteria.
 *
 * */
public interface MessageStrategy {
    void sendMessage(String recipient, String message);
}