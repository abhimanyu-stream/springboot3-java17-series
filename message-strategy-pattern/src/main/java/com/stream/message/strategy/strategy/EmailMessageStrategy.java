package com.stream.message.strategy.strategy;

import org.springframework.stereotype.Component;

@Component("email")
public class EmailMessageStrategy implements MessageStrategy {
    @Override
    public void sendMessage(String recipient, String message) {
        System.out.println("Sending Email to " + recipient + " with message: " + message);
        // Implement actual email-sending logic here (e.g., using JavaMailSender)
    }
}