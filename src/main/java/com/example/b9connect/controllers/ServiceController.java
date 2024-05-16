package com.example.b9connect.controllers;

import com.example.b9connect.dto.ServiceTO;
import com.example.b9connect.dto.TicketTO;
import com.example.b9connect.entities.Message;
import com.example.b9connect.entities.Service;
import com.example.b9connect.entities.Ticket;
import com.example.b9connect.entities.User;
import com.example.b9connect.services.FaqService;
import com.example.b9connect.services.MessageService;
import com.example.b9connect.services.ServiceService;
import com.example.b9connect.services.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.expression.Messages;

import java.util.Set;

@RestController
@RequiredArgsConstructor
public class ServiceController {
    private final ServiceService serviceService;
    private final FaqService faqService;
    private final TicketService ticketService;
    private final MessageService messageService;

    @GetMapping("/api/v1/services")
    @ResponseStatus(HttpStatus.OK)
    public Set<Service> getServices() {
        return serviceService.getServices();
    }

    @PostMapping("/api/v1/services/")
    @ResponseStatus(HttpStatus.OK)
    public Service addService(@RequestBody ServiceTO serviceTO, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return serviceService.addService(user, serviceTO);
    }

    @GetMapping("/api/v1/services/{service_id}")
    @ResponseStatus(HttpStatus.OK)
    public com.example.b9connect.entities.Service getServiceById(@PathVariable(name = "service_id", required = true) Long service_id) {
        return serviceService.getServiceById(service_id);
    }

    @GetMapping("/api/v1/services/faq/{service_id}")
    @ResponseStatus(HttpStatus.OK)
    public Set<com.example.b9connect.entities.Faqs> getFaqByServiceId(@PathVariable(name = "service_id", required = true) Long service_id) {
        return faqService.getFaqByServiceId(service_id);
    }

    @PostMapping("/api/v1/services/ticket")
    @ResponseStatus(HttpStatus.OK)
    public TicketTO addTicket(@RequestBody TicketTO ticketTO, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return ticketService.addTicket(ticketTO, user);
    }

    @GetMapping("/api/v1/services/ticket")
    @ResponseStatus(HttpStatus.OK)
    public Set<Ticket> getAllTickets(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return ticketService.getAllTickets(user);
    }

    @GetMapping("/api/v1/services/ticket/chat/{ticket_id}")
    @ResponseStatus(HttpStatus.OK)
    public Set<Message> getAllTickets(@PathVariable(name = "ticket_id", required = true) Long ticket_id, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return messageService.getMessages(ticket_id);
    }
}
