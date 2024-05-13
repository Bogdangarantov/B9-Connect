package com.example.b9connect.services;

import com.example.b9connect.dto.ServiceTO;
import com.example.b9connect.dto.TicketTO;
import com.example.b9connect.entities.Faqs;
import com.example.b9connect.entities.Ticket;
import com.example.b9connect.entities.User;
import com.example.b9connect.repos.ServicesRepository;
import com.example.b9connect.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ServiceService {
    private final ServicesRepository servicesRepository;
    private final UserRepository userRepository;

    public com.example.b9connect.entities.Service addService(User user, ServiceTO serviceTO) {
        com.example.b9connect.entities.Service service = mapToService(user.getId(), serviceTO);
        servicesRepository.save(service);
        return service;
    }

    // TODO
    private com.example.b9connect.entities.Service mapToService(Long ownerId, ServiceTO serviceTO) {
        com.example.b9connect.entities.Service service =

                com.example.b9connect.entities.Service.builder()
                .name(serviceTO.name())
                .description(serviceTO.description())
                .contact_email(serviceTO.contactEmail())
                .servicesUsers(serviceTO.users().stream()
                        .map(userRepository::findUserById)
                        .collect(Collectors.toSet())
                ).servicesFaqs(new HashSet<>())
                .build();
        serviceTO.faq().stream().forEach(service::addFaq);
        return service;
    }

    public Set<com.example.b9connect.entities.Service> getServices() {
        return servicesRepository.findAllServices();
    }

    public com.example.b9connect.entities.Service getServiceById(Long id) {
        return servicesRepository.findServiceById(id);
    }

}
