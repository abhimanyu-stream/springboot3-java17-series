package com.stream.message.adapter.adapter;

import org.springframework.stereotype.Service;
// Adapter for WhatsApp
@Service
public class WhatsAppAdapter implements MessageService {
    private final WhatsAppService whatsAppService;

    public WhatsAppAdapter() {
        this.whatsAppService = new WhatsAppService();
    }

    @Override
    public void sendMessage(String recipient, String message) {
        whatsAppService.sendWhatsAppMessage(recipient, message);// Adapting sendWhatsAppMessage to sendMessage
    }
}