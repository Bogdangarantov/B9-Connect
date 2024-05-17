package com.example.b9connect.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.java.Log;
import org.hibernate.proxy.HibernateProxy;

import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String problem;
    private Long service_id;
    private Long owner_id;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(name = "tickets_users",
            joinColumns = @JoinColumn(name = "ticket_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @ToString.Exclude
    private Set<User> ticketUsers = new HashSet<>();



    @OneToMany(mappedBy = "ticket",fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private Set<Message> ticketMessages = new HashSet<>();

    public void addMessage(Message message){
        ticketMessages.add(message);
        message.setTicket(this);
    }


    @Column(insertable = false, updatable = false)
    private Instant created;
    @Column(insertable = false, updatable = false)
    private Instant modified;

    @Override
    public final int hashCode() {
        Class<?> entityClass;
        if (this instanceof HibernateProxy) {
            entityClass = ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass();
        } else {
            entityClass = getClass();
        }
        return Objects.hash(entityClass, getId());
    }


    @Override
    public final boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Ticket other = (Ticket) obj;
        return getId() != null && Objects.equals(getId(), other.getId());
    }
}
