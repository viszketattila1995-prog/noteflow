package com.attila.noteflow.service;

import com.attila.noteflow.domain.AppUser;
import com.attila.noteflow.dto.request.LoginRequest;
import com.attila.noteflow.dto.request.RegisterRequest;
import com.attila.noteflow.dto.response.AuthResponse;
import com.attila.noteflow.exception.EmailAlreadyUsedException;
import com.attila.noteflow.repository.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class AuthService {

    private final AppUserRepository appUserRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    public AuthResponse register(RegisterRequest request) {

        if (appUserRepository.existsAppUsersByEmail(request.email())) {
            throw new EmailAlreadyUsedException("Email already exists: " + request.email());
        }

        AppUser appUser = new AppUser();
        appUser.setEmail(request.email());
        appUser.setPasswordHash(passwordEncoder.encode(request.password()));
        AppUser saved = appUserRepository.save(appUser);

        String token = jwtService.generateToken(request.email());

        return new AuthResponse(token,
                "Bearer",
                jwtService.getExpirationTimeInMs(),
                saved.getEmail());
    }

    public AuthResponse login(LoginRequest request) {

        AppUser appUser = appUserRepository.findByEmail(request.email()).orElseThrow(() -> new BadCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(request.password(), appUser.getPasswordHash())) {
            throw new BadCredentialsException("Invalid email or password");
        }

        String token = jwtService.generateToken(request.email());

        return new AuthResponse(token,
                "Bearer",
                jwtService.getExpirationTimeInMs(),
                request.email());
    }
}
