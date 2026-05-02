package com.lokaly.dto.auth;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class LoginResponse {
    private String token;
    private String userType;
    private Long userId;
    private String role;
}
