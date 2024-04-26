package com.example.b9connect.entities;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
     MANAGER,USER;

    @Override
    public String getAuthority() {
        return name();
    }
}