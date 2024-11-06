package com.stream.message.adapter.adapter;

import org.springframework.stereotype.Service;
// Adapter for Email
@Service
public class EmailAdapter implements MessageService {
    private final EmailService emailService;

    public EmailAdapter() {
        this.emailService = new EmailService();
    }

    @Override
    public void sendMessage(String recipient, String message) {
        emailService.sendEmail(recipient, message);
    } // Adapting sendEmail to sendMessage
}