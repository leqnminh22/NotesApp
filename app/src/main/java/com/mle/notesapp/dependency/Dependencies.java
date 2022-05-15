package com.mle.notesapp.dependency;

import android.content.Context;

import com.mle.notesapp.domain.NotesRepository;
import com.mle.notesapp.domain.SharedPrefNotesRepository;

public class Dependencies {

    private static NotesRepository notesRepository;


    public static NotesRepository getNotesRepository(Context context) {

        if(null == notesRepository) {
            notesRepository = new SharedPrefNotesRepository(context);
        }
        return notesRepository;
    }
}
