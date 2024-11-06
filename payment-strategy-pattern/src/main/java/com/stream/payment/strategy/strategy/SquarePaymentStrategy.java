package com.stream.payment.strategy.strategy;

import org.springframework.stereotype.Component;

@Component("square")
public class SquarePaymentStrategy implements PaymentStrategy {
    @Override
    public void pay(double amount) {
        System.out.println("Processing Square payment of $" + amount);
        // Implement Square-specific payment logic here
    }
}