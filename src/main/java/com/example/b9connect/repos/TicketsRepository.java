package com.example.b9connect.repos;

import com.example.b9connect.entities.Faqs;
import com.example.b9connect.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface TicketsRepository extends JpaRepository<Ticket, Long> {

    @Query("SELECT t from Ticket t JOIN t.ticketUsers u where u.id = :userId")
    Set<Ticket> findTicketsByUser(Long userId);
}
