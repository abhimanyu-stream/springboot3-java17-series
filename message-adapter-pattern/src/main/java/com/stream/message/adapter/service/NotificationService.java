package com.stream.message.adapter.service;

import com.stream.message.adapter.adapter.EmailAdapter;
import com.stream.message.adapter.adapter.MessageService;
import com.stream.message.adapter.adapter.SMSAdapter;
import com.stream.message.adapter.adapter.WhatsAppAdapter;
import com.stream.message.adapter.model.MessageLog;
import com.stream.message.adapter.repository.MessageLogRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final MessageLogRepository messageLogRepository;
    private final EmailAdapter emailAdapter;
    private final SMSAdapter smsAdapter;
    private final WhatsAppAdapter whatsAppAdapter;

    @Autowired
    public NotificationService(MessageLogRepository messageLogRepository,
                               EmailAdapter emailAdapter,
                               SMSAdapter smsAdapter,
                               WhatsAppAdapter whatsAppAdapter) {
        this.messageLogRepository = messageLogRepository;
        this.emailAdapter = emailAdapter;
        this.smsAdapter = smsAdapter;
        this.whatsAppAdapter = whatsAppAdapter;
    }

    @Transactional
    public void sendNotification(String channel, String recipient, String message) {
        MessageService adapter;

        switch (channel.toLowerCase()) {
            case "email" -> adapter = emailAdapter;
            case "sms" -> adapter = smsAdapter;
            case "whatsapp" -> adapter = whatsAppAdapter;
            default -> throw new IllegalArgumentException("Unknown channel: " + channel);
        }

        adapter.sendMessage(recipient, message);
        logMessage(recipient, message, channel, "Sent");
    }

    private void logMessage(String recipient, String message, String channel, String status) {
        MessageLog log = new MessageLog();
        log.setRecipient(recipient);
        log.setMessage(message);
        log.setChannel(channel);
        log.setStatus(status);
        messageLogRepository.save(log);
    }
}