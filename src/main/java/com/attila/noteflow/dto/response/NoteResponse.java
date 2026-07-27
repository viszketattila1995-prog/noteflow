package com.attila.noteflow.dto.response;

import java.util.UUID;

public record NoteResponse(
        Long id,
        String title,
        String content
) {
}
