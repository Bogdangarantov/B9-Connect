package com.example.b9connect.repos;

import com.example.b9connect.entities.Service;
import com.example.b9connect.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ServicesRepository extends JpaRepository<Service, Long> {
    @Query("SELECT p from Service p")
    Set<Service> findAllServices();
    @Query("SELECT p from Service p where p.id = :id")
    Service findServiceById(Long id);
}
