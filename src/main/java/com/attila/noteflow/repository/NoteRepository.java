package com.attila.noteflow.repository;

import com.attila.noteflow.domain.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findAllByAppUserId(UUID uuid);
}
