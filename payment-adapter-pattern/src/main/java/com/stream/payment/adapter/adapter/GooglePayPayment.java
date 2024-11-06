package com.stream.payment.adapter.adapter;

// A third-party Google Pay payment class with a different API
public class GooglePayPayment {
    public void googlePayCharge(double amount) {
        System.out.println("Processing Google Pay payment of $" + amount);
    }
}