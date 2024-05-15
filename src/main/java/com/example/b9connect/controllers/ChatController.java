package com.example.b9connect.controllers;

import com.example.b9connect.entities.Message;
import com.example.b9connect.entities.User;
import com.example.b9connect.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.Set;

@RequiredArgsConstructor
@Controller
public class ChatController {
    @Autowired MessageService messageService;
    @MessageMapping("/chat/{chatId}.sendMessage")
    @SendTo("/topic/{chatId}/public")
    public Message sendMessage(@PathVariable String chatId, @RequestBody Message message, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return messageService.addMessage(message,user);
    }
}