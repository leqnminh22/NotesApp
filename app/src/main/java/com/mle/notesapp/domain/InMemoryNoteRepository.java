package com.mle.notesapp.domain;

import android.content.Context;

import com.mle.notesapp.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class InMemoryNoteRepository implements NotesRepository {

    private static InMemoryNoteRepository INSTANCE;
    private Context context;
    private ArrayList<Note> data = new ArrayList<>();

    private InMemoryNoteRepository(Context context) {
        this.context = context;
    }

    public static InMemoryNoteRepository getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new InMemoryNoteRepository(context);
        }
        return INSTANCE;
    }

    @Override
    public List<Note> getAll() {
        data.add(new Note(UUID.randomUUID().toString(), "Title 1", "Description 1", new Date()));
        data.add(new Note(UUID.randomUUID().toString(), "Title 2", "Description 2", new Date()));
        data.add(new Note(UUID.randomUUID().toString(), "Title 3", "Description 3", new Date()));
        data.add(new Note(UUID.randomUUID().toString(), "Title 4", "Description 4", new Date()));
        data.add(new Note(UUID.randomUUID().toString(), "Title 5", "Description 5", new Date()));
        data.add(new Note(UUID.randomUUID().toString(), "Title 6", "Description 6", new Date()));

        for (int i = 0; i < 50; i++) {

            data.add(new Note(UUID.randomUUID().toString(), "Title #", "Description #", new Date()));

        }

        return data;
    }

    @Override
    public void add(Note note) {

    }
}
