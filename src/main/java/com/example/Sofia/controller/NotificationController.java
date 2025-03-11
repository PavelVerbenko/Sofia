package com.example.Sofia.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
//для уведомлений
@Controller
public class NotificationController {

    @MessageMapping("/notify")
    @SendTo("/topic/notifications")
    public String sendNotification(String message) {
        return message;
    }

}
