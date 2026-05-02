package com.lokaly.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginRequest {
    @NotBlank
    private String login;
    @NotBlank
    private String motDePasse;
}
