package com.attila.noteflow.service;

import com.attila.noteflow.domain.AppUser;
import com.attila.noteflow.domain.Note;
import com.attila.noteflow.dto.request.CreateNoteRequest;
import com.attila.noteflow.dto.response.NoteResponse;
import com.attila.noteflow.exception.NoteNotFoundException;
import com.attila.noteflow.repository.NoteRepository;
import com.attila.noteflow.security.CurrentUserProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class NoteService {

    private final NoteRepository noteRepository;
    private final CurrentUserProvider currentUserProvider;


    public NoteResponse createNote(CreateNoteRequest createNote) {
        AppUser appUser = currentUserProvider.getCurrentUser();

        Note note = new Note();
        note.setAppUser(appUser);
        note.setTitle(createNote.title());
        note.setContent(createNote.content());
        Note saved = noteRepository.save(note);
        log.info("Note created with id {}", saved.getId());

        return new NoteResponse(saved.getId(), saved.getTitle(), saved.getContent());
    }

    @Transactional(readOnly = true)
    public List<NoteResponse> getAllNotes() {
        AppUser appUser = currentUserProvider.getCurrentUser();

        List<Note> noteList = noteRepository.findAllByAppUserId(appUser.getId());
        log.info("Get all notes by app user");

        return noteList.stream()
                .map(n -> new NoteResponse(n.getId(), n.getTitle(), n.getContent()))
                .toList();
    }

    @Transactional(readOnly = true)
    public NoteResponse getNotById(Long id) {
        AppUser appUser = currentUserProvider.getCurrentUser();
        Note note = getOwnedNote(id, appUser);

        log.info("Get not by id: {}", id);
        return new NoteResponse(note.getId(), note.getTitle(), note.getContent());
    }

    public void deleteNoteById(Long id) {
        AppUser appUser = currentUserProvider.getCurrentUser();
        Note note = getOwnedNote(id, appUser);

        log.info("delete note by id: {}", id);
        noteRepository.delete(note);
    }

    private Note getOwnedNote(Long id, AppUser appUser) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException("Note not found with id: " + id));
        if (!note.getAppUser().getId().equals(appUser.getId())) {
            throw new NoteNotFoundException("Note " + id + " doesn't belong to user " + appUser.getId());
        }
        return note;
    }
}
