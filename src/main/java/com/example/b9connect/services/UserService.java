package com.example.b9connect.services;

import com.example.b9connect.dto.UserTO;
import com.example.b9connect.entities.User;
import com.example.b9connect.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private Set<UserTO> mapUsersToUserTO(Set<User> users) {
        return users.stream().map(user -> new UserTO(user.getName(), user.getId(), user.getRoles(), user.getLogin(), user.getEmail(), null)).collect(Collectors.toSet());
    }
    public Set<UserTO> getAllUsers() {
        return mapUsersToUserTO(userRepository.getAll());
    }
}
