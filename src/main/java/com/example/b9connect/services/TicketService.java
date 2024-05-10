package com.example.b9connect.services;

import com.example.b9connect.entities.Ticket;
import com.example.b9connect.repos.ServicesRepository;
import com.example.b9connect.repos.TicketsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TicketService {
    private final TicketsRepository ticketsRepository;

    public Ticket addTicket(Ticket ticket){
        ticketsRepository.save(ticket);
        return  ticket;
    }

}
