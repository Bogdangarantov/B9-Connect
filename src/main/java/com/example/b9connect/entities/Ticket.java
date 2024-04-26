package com.example.b9connect.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import org.hibernate.proxy.HibernateProxy;

import java.time.Instant;
import java.util.Objects;

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
    private String description;
    private Long service_id;
    private Long owner_id;
    private Instant created;
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
