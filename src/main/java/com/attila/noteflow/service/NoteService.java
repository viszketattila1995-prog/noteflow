package com.attila.noteflow.service;

import com.attila.noteflow.dto.request.CreateNoteRequest;
import com.attila.noteflow.dto.response.NoteResponse;
import com.attila.noteflow.repository.AppUserRepository;
import com.attila.noteflow.repository.NoteRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final AppUserRepository appUserRepository;


    public NoteResponse createNote(CreateNoteRequest createNote) {
    }
}
