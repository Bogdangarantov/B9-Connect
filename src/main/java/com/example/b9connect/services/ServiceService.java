package com.example.b9connect.services;

import com.example.b9connect.repos.ServicesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class ServiceService {
    private final ServicesRepository servicesRepository;

    public Set<com.example.b9connect.entities.Service> getServices(){
        return servicesRepository.findAllServices();
    }

    public com.example.b9connect.entities.Service getServiceById(Integer id){
        return servicesRepository.findServiceById(id);
    }

}
