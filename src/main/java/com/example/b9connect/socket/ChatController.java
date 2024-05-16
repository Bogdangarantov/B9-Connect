package com.example.b9connect.socket;

import com.example.b9connect.dto.ChatUserTO;
import com.example.b9connect.entities.Message;
import com.example.b9connect.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
public class ChatController {
    @Autowired
    TicketService ticketService;
    private Map<Long, List<ChatUserTO>> chatUsersMap = new HashMap<>();

    //    @MessageMapping("/chat.sendMessage")
//    @SendTo("/topic/public")
//    public Message sendMessage(@Payload Message message) {
//        return message;
//    }
    @MessageMapping("/chat/{chatId}/sendMessage")
    @SendTo("/topic/{chatId}")
    public Message sendMessage(@Payload Message message,
                               @DestinationVariable Long chatId,SimpMessageHeaderAccessor headerAccessor) {
        System.out.println(message.toString());
//        message.setTicket(ticketService.getTicketById(chatId));
//        ticketService.addMessageToTicket(chatId,message);
        return message;
    }

    //    @MessageMapping("/chat/{chatId}/addUser")
//    @SendTo("/topic/public")
//    public ChatUserTO addUser(@Payload ChatUserTO chatUserTO,
//                              SimpMessageHeaderAccessor headerAccessor,
//                              @DestinationVariable String chatId) {
//        // Add username in web socket session
//
//        Objects.requireNonNull(headerAccessor.getSessionAttributes()).put("username", chatMessage.getSender());
//        return chatUserTO;
//    }
    @MessageMapping("/chat/{chatId}/addUser")
    @SendTo("/topic/{chatId}")
    public void addUser(@Payload ChatUserTO chatUserTO,
                              SimpMessageHeaderAccessor headerAccessor,
                              @DestinationVariable Long chatId) {
        String name = chatUserTO.name();
        System.out.println(chatUserTO.toString());
        System.out.println(chatId);
        Long id = chatUserTO.id();
        List<ChatUserTO> chatUsers = chatUsersMap.getOrDefault(chatId, new ArrayList<>());
        chatUsers.add(new ChatUserTO(chatUserTO.name(), chatUserTO.id()));
        chatUsersMap.put(chatId, chatUsers);
        Objects.requireNonNull(headerAccessor.getSessionAttributes()).put("name", name);
        Objects.requireNonNull(headerAccessor.getSessionAttributes()).put("id", id);
    }
}