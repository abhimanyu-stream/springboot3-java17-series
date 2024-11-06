package com.stream.message.strategy.strategy;

import org.springframework.stereotype.Component;

@Component("sms")
public class SmsMessageStrategy implements MessageStrategy {
    @Override
    public void sendMessage(String recipient, String message) {
        System.out.println("Sending SMS to " + recipient + " with message: " + message);
        // Implement actual SMS-sending logic here (e.g., using Twilio API)
    }
}