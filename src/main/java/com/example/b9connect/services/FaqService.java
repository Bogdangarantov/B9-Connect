package com.example.b9connect.services;

import com.example.b9connect.entities.Faqs;
import com.example.b9connect.repos.FaqsRepository;
import com.example.b9connect.repos.ServicesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@RequiredArgsConstructor
@Service
public class FaqService {
    private final FaqsRepository faqsRepository;

    public Set<Faqs> getFaqByServiceId(Long id) {
        return faqsRepository.findAllByServiceId(id);
    }

}
