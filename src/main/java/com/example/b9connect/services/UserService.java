package com.example.b9connect.services;

import com.example.b9connect.dto.UserTO;
import com.example.b9connect.entities.User;
import com.example.b9connect.entities.UserRole;
import com.example.b9connect.repos.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private Set<UserTO> mapUsersToUserTO(Set<User> users) {
        return users.stream().map(user -> new UserTO(user.getName(), user.getId(), user.getRoles(), user.getLogin(), user.getEmail(), null)).collect(Collectors.toSet());
    }
    public Set<UserTO> getAllUsers() {
        return mapUsersToUserTO(userRepository.getAll());
    }
    private User mapToUser(UserTO userTO) {
        return User.builder()
                .login(userTO.login())
                .email(userTO.email())
                .roles(userTO.roles())
                .password(passwordEncoder.encode(userTO.password()))
                .enabled(true)
                .name(userTO.name())
                .build();
    }

    @Transactional
    public User addNewUser(UserTO newUser) {
        User user = mapToUser(newUser);
        return userRepository.save(user);
    }
}
