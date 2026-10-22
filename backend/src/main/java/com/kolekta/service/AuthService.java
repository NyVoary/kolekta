package com.kolekta.service;

import com.kolekta.dto.auth.LoginRequest;
import com.kolekta.dto.auth.LoginResponse;

public interface AuthService {
    LoginResponse loginEmploye(LoginRequest request);
    LoginResponse loginClient(LoginRequest request);
}
