package com.example.b9connect.repos;

import com.example.b9connect.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("Select u FROM User u join fetch u.roles where u.login = :login")
    Optional<User> findUserByLogin(String login);
    @Query("Select u FROM User u join fetch u.roles where u.id = :id")
    User findUserById(Long id);
    @Query("SELECT u FROM User u where u.enabled = true")
    Set<User> getAll();


}
