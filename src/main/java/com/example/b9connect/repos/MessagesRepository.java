package com.example.b9connect.repos;

import com.example.b9connect.entities.Message;
import com.example.b9connect.entities.Service;
import com.example.b9connect.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface MessagesRepository extends JpaRepository<Message, Long> {
    @Query("SELECT m from Message m where m.ticket_id = :ticketId")
    Set<Message> findAllMessagesByTicket(Long ticketId);

}
