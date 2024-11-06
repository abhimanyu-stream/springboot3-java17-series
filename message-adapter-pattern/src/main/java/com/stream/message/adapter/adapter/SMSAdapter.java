package com.stream.message.adapter.adapter;

import org.springframework.stereotype.Service;
// Adapter for SMS
@Service
public class SMSAdapter implements MessageService {
    private final SMSService smsService;

    public SMSAdapter() {
        this.smsService = new SMSService();
    }

    @Override
    public void sendMessage(String recipient, String message) {
        smsService.sendSMS(recipient, message);
    } // Adapting sendSMS to sendMessage
}