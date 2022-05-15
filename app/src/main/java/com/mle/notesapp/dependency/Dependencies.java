package com.mle.notesapp.dependency;

import com.mle.notesapp.domain.FireStoreNotesRepository;
import com.mle.notesapp.domain.NotesRepository;

public class Dependencies {

    private static final NotesRepository NOTES_REPOSITORY = new FireStoreNotesRepository();


    public static NotesRepository getNotesRepository() {
        return NOTES_REPOSITORY;
    }
}
