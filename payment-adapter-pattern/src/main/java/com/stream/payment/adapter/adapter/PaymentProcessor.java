package com.stream.payment.adapter.adapter;
// The target interface that all payment adapters will implement
public interface PaymentProcessor {
    void processPayment(double amount);
}
