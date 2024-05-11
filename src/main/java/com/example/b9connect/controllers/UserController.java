package com.example.b9connect.controllers;

import com.example.b9connect.dto.UserTO;
import com.example.b9connect.entities.User;
import com.example.b9connect.exceptions.DuplicateEntityException;
import com.example.b9connect.services.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Set<UserTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<User> addUser(@RequestBody UserTO userTO, Authentication authentication){
        try {
            User persistedUser = userService.addNewUser(userTO);
            var location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(persistedUser.getId())
                    .toUri();
            return ResponseEntity.created(location)
                    .body(persistedUser);
        } catch (Exception e) {
            throw new DuplicateEntityException(String.format("User with login %s already exist", userTO.login()));
        }
    }
}
