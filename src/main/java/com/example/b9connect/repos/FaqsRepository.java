package com.example.b9connect.repos;

import com.example.b9connect.entities.Faqs;
import com.example.b9connect.entities.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface FaqsRepository extends JpaRepository<Faqs, Long> {
    @Query("SELECT f from Faqs f where f.service_id = :id")
    Set<Faqs> findAllByServiceId(Integer id);
}
