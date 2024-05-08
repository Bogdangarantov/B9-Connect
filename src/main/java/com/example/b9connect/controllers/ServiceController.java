package com.example.b9connect.controllers;

import com.example.b9connect.entities.Service;
import com.example.b9connect.entities.User;
import com.example.b9connect.services.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class ServiceController {
    private final ServiceService serviceService;
    @GetMapping("/api/v1/services")
    @ResponseStatus(HttpStatus.OK)
    public Set<Service> addProject(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return serviceService.getServices();
    }
}
