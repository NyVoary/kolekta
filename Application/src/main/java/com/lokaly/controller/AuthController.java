package com.lokaly.controller;

import com.lokaly.dto.auth.LoginRequest;
import com.lokaly.dto.auth.LoginResponse;
import com.lokaly.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/employe/login")
    public ResponseEntity<LoginResponse> loginEmploye(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.loginEmploye(request));
    }

    @PostMapping("/client/login")
    public ResponseEntity<LoginResponse> loginClient(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.loginClient(request));
    }
}
