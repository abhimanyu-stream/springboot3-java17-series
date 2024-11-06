package com.stream.payment.adapter.adapter;

// A third-party PayPal payment class with a different API
public class PayPalPayment {
    public void payWithPayPal(double amount) {
        System.out.println("Processing PayPal payment of $" + amount);
    }
}