package com.attila.noteflow.service;

import com.attila.noteflow.dto.request.LoginRequest;
import com.attila.noteflow.dto.request.RegisterRequest;
import com.attila.noteflow.dto.response.AuthResponse;
import com.attila.noteflow.repository.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class AuthService {

    private final AppUserRepository appUserRepository;

    public AuthResponse register(RegisterRequest request) {
    }

    public AuthResponse login(LoginRequest request) {
    }
}
