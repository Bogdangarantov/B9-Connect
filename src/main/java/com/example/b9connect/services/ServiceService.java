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

//    public com.example.b9connect.entities.Service addService(User user, ServiceTO service) {
//        com.example.b9connect.entities.Service service_ = mapToService(user.getId(), service);
//        servicesRepository.save(service_);
//        return service_;
//    }
//
//    // TODO
//    private com.example.b9connect.entities.Service mapToService(Long ownerId, ServiceTO serviceTO) {
//        return com.example.b9connect.entities.Service.builder()
//                .name(serviceTO.name())
//                .description(serviceTO.description())
//                .servicesUsers(serviceTO.users().stream()
//                        .map(userRepository::findUserById)
//                        .collect(Collectors.toSet())
//                )
//                .servicesFaqs(serviceTO.faq().stream()
//                        .map(faq -> {
//                            return Faqs.builder()
//                                    .question(faq.getQuestion())
//                                    .answer(faq.getAnswer()).build();
//                        })
//                        .collect(Collectors.toSet())
//                ).build();
//    }

    public Set<com.example.b9connect.entities.Service> getServices() {
        return servicesRepository.findAllServices();
    }

    public com.example.b9connect.entities.Service getServiceById(Integer id) {
        return servicesRepository.findServiceById(id);
    }

}
