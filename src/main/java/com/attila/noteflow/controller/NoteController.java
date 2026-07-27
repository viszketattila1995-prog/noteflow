package com.attila.noteflow.controller;

import com.attila.noteflow.dto.request.CreateNoteRequest;
import com.attila.noteflow.dto.response.NoteResponse;
import com.attila.noteflow.service.NoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @PostMapping
    public ResponseEntity<NoteResponse> createNote(@Valid @RequestBody CreateNoteRequest createNote) {
        return ResponseEntity.status(HttpStatus.CREATED).body(noteService.createNote(createNote));
    }

    @GetMapping
    public ResponseEntity<List<NoteResponse>> getAllNotes() {
        List<NoteResponse> noteResponseList = noteService.getAllNotes();
        return ResponseEntity.ok(noteResponseList);
    }

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<NoteResponse> getNoteById(@PathVariable Long id) {
        return ResponseEntity.ok(noteService.getNotById(id));
    }

    @DeleteMapping("/{id:\\d+}")
    public ResponseEntity<Void> deleteNotById(@PathVariable Long id) {
        noteService.deleteNoteById(id);
        return ResponseEntity.noContent().build();
    }
}
