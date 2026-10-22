package com.kolekta.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
public class CustomUserDetails implements UserDetails {

    private final Long id;
    private final String username;
    private final String password;
    private final String userType; // "EMPLOYE" | "CLIENT"
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(Long id, String username, String password, String role, String userType) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.userType = userType;
        this.authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()));
    }

    @Override public boolean isAccountNonExpired()  { return true; }
    @Override public boolean isAccountNonLocked()   { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled()            { return true; }
}
