package com.example.b9connect.services;

import com.example.b9connect.entities.Message;
import com.example.b9connect.entities.Ticket;
import com.example.b9connect.entities.User;
import com.example.b9connect.repos.MessagesRepository;
import com.example.b9connect.repos.TicketsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@RequiredArgsConstructor
@Service
public class MessageService {
    private final MessagesRepository messagesRepository;
    private final TicketsRepository ticketsRepository;

    public Set<Message> getMessages(Long ticketId){
        return messagesRepository.findAllMessagesByTicket(ticketId);
    }
    public Message addMessage(Long chatId, Message message){
        Ticket ticket = ticketsRepository.findTicketById(chatId);
        message.setTicket(ticket);
        return messagesRepository.save(message);
    }
    public Message getMessageById(Long messageId){
        return messagesRepository.findById(messageId).get();
    }
}
