package com.example.b9connect.socket;

import com.example.b9connect.dto.ChatUserTO;
import com.example.b9connect.entities.Message;
import com.example.b9connect.entities.Ticket;
import com.example.b9connect.entities.User;
import com.example.b9connect.repos.UserRepository;
import com.example.b9connect.services.MessageService;
import com.example.b9connect.services.TicketService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
@RequiredArgsConstructor
public class ChatController {
    @Autowired
    TicketService ticketService;
    @Autowired
    MessageService messageService;

    private final UserRepository userRepository;


    private final EntityManager entityManager;
    private Map<Long, List<ChatUserTO>> chatUsersMap = new HashMap<>();

    //    @MessageMapping("/chat.sendMessage")
//    @SendTo("/topic/public")
//    public Message sendMessage(@Payload Message message) {
//        return message;
//    }
    @MessageMapping("/chat/{chatId}/sendMessage")
    @SendTo("/topic/{chatId}")
    @Transactional
    public Message sendMessage(@Payload Message message,
                               @DestinationVariable Long chatId, Authentication authentication) {
        System.out.println(message.toString());
        User user= entityManager.merge((User) authentication.getPrincipal());
        message.setUser(user);
        Message msg =messageService.addMessage(chatId,message);
        return msg;
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