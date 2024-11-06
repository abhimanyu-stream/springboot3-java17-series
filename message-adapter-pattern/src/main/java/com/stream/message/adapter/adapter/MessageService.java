package com.stream.message.adapter.adapter;
// Message interface that all adapters must implement
public interface MessageService {
    void sendMessage(String recipient, String message);
}