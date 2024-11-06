package com.stream.payment.strategy.strategy;


//The Strategy Pattern is ideal for a situation where multiple algorithms (or strategies) can be applied to a specific task.
// In this example, we’ll use the Strategy Pattern to implement an e-commerce application that can handle different payment methods (like PayPal, Stripe, and Square), making it easy to switch between them without modifying the core logic of the application.

public interface PaymentStrategy {
    void pay(double amount);
}