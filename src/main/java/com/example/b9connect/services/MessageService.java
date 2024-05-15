package com.example.b9connect.services;

import com.example.b9connect.entities.Message;
import com.example.b9connect.entities.User;
import com.example.b9connect.repos.MessagesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@RequiredArgsConstructor
@Service
public class MessageService {
    private final MessagesRepository messagesRepository;

    public Set<Message> getMessages(Long ticketId){
        return messagesRepository.findAllMessagesByTicket(ticketId);
    }
    public Message addMessage(Message message, User user){
        message.setUser_id(user.getId());
        return messagesRepository.save(message);
    }

}
