package com.attila.noteflow.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateNoteRequest(
        @NotBlank String title,
        String content
) {

}
