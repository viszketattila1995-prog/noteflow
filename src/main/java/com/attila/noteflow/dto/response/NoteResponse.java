package com.attila.noteflow.dto.response;

public record NoteResponse(
        Long id,
        String title,
        String content
) {
}
