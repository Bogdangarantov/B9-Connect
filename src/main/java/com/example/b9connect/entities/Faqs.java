package com.example.b9connect.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "faqs")
public class Faqs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long service_id;
    private String question;
    private String answer;


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
        Faqs other = (Faqs) obj;
        return getId() != null && Objects.equals(getId(), other.getId());
    }

}
