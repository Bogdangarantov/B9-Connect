package com.example.b9connect.services;

import com.example.b9connect.dto.TicketTO;
import com.example.b9connect.entities.Ticket;
import com.example.b9connect.entities.User;
import com.example.b9connect.repos.ServicesRepository;
import com.example.b9connect.repos.TicketsRepository;
import com.example.b9connect.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@RequiredArgsConstructor
@Service
public class TicketService {
    private final TicketsRepository ticketsRepository;
    private final UserRepository userRepository;
    private final ServicesRepository servicesRepository;

    public TicketTO addTicket(TicketTO ticketTO, User user){
        Ticket ticket = mapToTicket(user.getId(),ticketTO);
//        ticket.getTicketUsers().add(user);
        com.example.b9connect.entities.Service service = servicesRepository.findServiceById(ticketTO.serviceId());
        service.getServicesUsers().stream().forEach(sUser->ticket.getTicketUsers().add(sUser));
        System.out.println(ticket);
        ticketsRepository.saveAndFlush(ticket);
        return new TicketTO(ticket.getId(), ticket.getName(), ticket.getProblem(),ticket.getService_id());
    }
    private Ticket mapToTicket(Long ownerId, TicketTO ticketTO) {
        return Ticket.builder()
                .owner_id(userRepository.getReferenceById(ownerId).getId())
                .name(ticketTO.name()).problem(ticketTO.problem())
                .service_id(ticketTO.serviceId())
                .ticketUsers(new HashSet<>())
                .build();
    }
}
