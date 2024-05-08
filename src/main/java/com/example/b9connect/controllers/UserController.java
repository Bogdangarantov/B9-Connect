package com.example.b9connect.controllers;

import com.example.b9connect.dto.UserTO;
import com.example.b9connect.services.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
}
