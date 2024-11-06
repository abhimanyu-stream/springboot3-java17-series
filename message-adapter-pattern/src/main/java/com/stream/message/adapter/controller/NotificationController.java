package com.stream.message.adapter.controller;

import com.stream.message.adapter.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/send")
    public String sendNotification(@RequestParam String channel,
                                   @RequestParam String recipient,
                                   @RequestParam String message) {
        notificationService.sendNotification(channel, recipient, message);
        return "Message sent via " + channel;
    }
}