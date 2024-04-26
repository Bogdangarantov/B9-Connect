package com.example.b9connect.repos;

import com.example.b9connect.entities.Service;
import com.example.b9connect.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicesRepository extends JpaRepository<Service, Long> {
}
