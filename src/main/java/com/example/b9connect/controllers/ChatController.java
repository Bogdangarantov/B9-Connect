package com.example.b9connect.controllers;

import com.example.b9connect.entities.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.Date;

@Controller
public class ChatController {

    @MessageMapping("/sendMessage") // Цей мапінг відповідає адресі, на яку клієнт надсилає повідомлення
    @SendTo("/topic/messages") // Цей топік, до якого буде розіслано відповідь клієнтам
    public Message sendMessage(@Payload Message message) {
        // Обробляємо повідомлення
        return message;
    }
}