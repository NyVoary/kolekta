package com.lokaly.service;

import com.lokaly.dto.auth.LoginRequest;
import com.lokaly.dto.auth.LoginResponse;

public interface AuthService {
    LoginResponse loginEmploye(LoginRequest request);
    LoginResponse loginClient(LoginRequest request);
}
