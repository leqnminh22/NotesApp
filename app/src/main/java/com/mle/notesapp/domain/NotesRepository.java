package com.mle.notesapp.domain;

import java.util.List;

public interface NotesRepository {

    void getAll(Callback<List<Note>> callback);

    void add(String title, String message, Callback<Note> callback);
}
