package com.example.b9connect.repos;

import com.example.b9connect.entities.Faqs;
import com.example.b9connect.entities.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FaqsRepository extends JpaRepository<Faqs, Long> {
}
