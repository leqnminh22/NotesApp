package com.mle.notesapp.domain;

import android.content.Context;

import com.mle.notesapp.R;

import java.util.ArrayList;
import java.util.List;

public class InMemoryNoteRepository implements NotesRepository {

    private static InMemoryNoteRepository INSTANCE;
    private Context context;

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
        ArrayList<Note> result = new ArrayList<>();
        result.add(new Note(context.getString(R.string.birthday), context.getString(R.string.birthday_info), context.getString(R.string.birthday_date)));
        result.add(new Note(context.getString(R.string.gym), context.getString(R.string.gym_info), context.getString(R.string.gym_date)));
        result.add(new Note(context.getString(R.string.exam), context.getString(R.string.exam_info), context.getString(R.string.exam_date)));
        result.add(new Note(context.getString(R.string.interview), context.getString(R.string.interview_info), context.getString(R.string.interview_date)));
        result.add(new Note(context.getString(R.string.football), context.getString(R.string.football_info), context.getString(R.string.football_date)));
        return result;
    }

    @Override
    public void add(Note note) {

    }
}
