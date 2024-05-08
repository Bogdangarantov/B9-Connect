package com.example.b9connect.dto;

import com.example.b9connect.entities.UserRole;

import java.util.Set;

public record UserTO(String name, Long id, Set<UserRole> roles, String login,String email, String pass) {
}
