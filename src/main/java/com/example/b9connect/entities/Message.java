package com.example.b9connect.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.Instant;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;

    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "ticket_id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    @ToString.Exclude
    private Ticket ticket;

    @Column(insertable = false, updatable = false)
    @org.hibernate.annotations.Generated()
    private Instant created;






    @ManyToOne(fetch = FetchType.EAGER ,cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "user_id",nullable = false)
    @ToString.Exclude
    private User user;

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
        Message other = (Message) obj;
        return getId() != null && Objects.equals(getId(), other.getId());
    }
}
