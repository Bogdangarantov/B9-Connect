package com.example.b9connect.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    private Long ticket_id;
    private Long user_id ;
    private String message;
    private Instant created;

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
