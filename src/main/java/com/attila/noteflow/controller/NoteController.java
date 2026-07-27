package com.attila.noteflow.controller;

import com.attila.noteflow.dto.request.CreateNoteRequest;
import com.attila.noteflow.dto.response.NoteResponse;
import com.attila.noteflow.service.NoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @PostMapping
    public ResponseEntity<NoteResponse> createNote(@Valid @RequestBody CreateNoteRequest createNote) {
        return ResponseEntity.status(HttpStatus.CREATED).body(noteService.createNote(createNote));
    }
}
