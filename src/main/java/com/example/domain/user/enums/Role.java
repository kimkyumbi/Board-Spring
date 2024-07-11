package com.example.domain.user.enums;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
public enum Role implements GrantedAuthority {
    ROLE_ADMIN("ROLE_USER"),
    ROLE_USER("ROLE_USER");

    private final String name;

    @Override
    public String getAuthority() {
        return name;
    }
}
