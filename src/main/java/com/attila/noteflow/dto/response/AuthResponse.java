package com.attila.noteflow.dto.response;

public record AuthResponse(
        String accessToken,
        String tokenType,
        long expiresIn,
        String email
) {
}
