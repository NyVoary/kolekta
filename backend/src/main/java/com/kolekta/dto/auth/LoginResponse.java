package com.kolekta.dto.auth;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class LoginResponse {
    private String token;
    private String userType;
    private Long userId;
    private String role;
    private String nom;
    private String prenoms;
}
