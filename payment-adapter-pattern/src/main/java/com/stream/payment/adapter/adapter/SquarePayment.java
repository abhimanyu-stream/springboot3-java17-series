package com.stream.payment.adapter.adapter;

// A third-party Square Pay payment class with a different API
public class SquarePayment {
    public void processSquarePayment(double amount) {
        System.out.println("Processing Square payment of $" + amount);
    }
}