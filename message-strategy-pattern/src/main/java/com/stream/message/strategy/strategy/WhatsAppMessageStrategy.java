package com.stream.message.strategy.strategy;

import org.springframework.stereotype.Component;

@Component("whatsapp")
public class WhatsAppMessageStrategy implements MessageStrategy {
    @Override
    public void sendMessage(String recipient, String message) {
        System.out.println("Sending WhatsApp message to " + recipient + " with message: " + message);
        // Implement actual WhatsApp-sending logic here (e.g., using WhatsApp API)
    }
}