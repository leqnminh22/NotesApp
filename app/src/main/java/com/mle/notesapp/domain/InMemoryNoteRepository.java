package com.mle.notesapp.domain;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class InMemoryNoteRepository implements NotesRepository {

    private static InMemoryNoteRepository INSTANCE;
    private Context context;
    private ArrayList<Note> data = new ArrayList<>();
    private Executor executor = Executors.newSingleThreadExecutor();
    private Handler handler = new Handler(Looper.getMainLooper());


    private InMemoryNoteRepository(Context context) {
        this.context = context;
    }

    public InMemoryNoteRepository()  {

    }


    @Override
    public void getAll(Callback<List<Note>> callback) {

        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        handler.post(new Runnable() {
            @Override
            public void run() {

                callback.onSuccess(data);

            }
        });

    }

    @Override
    public void add(String title, String message, Callback<Note> callback) {

        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Note note = new Note(UUID.randomUUID().toString(), title, message, new Date());

                data.add(note);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(note);
                    }
                });
            }
        });
    }

    @Override
    public void remove(Note note, Callback<Void> callback) {

        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                data.remove(note);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(null);
                    }
                });
            }
        });

    }

    @Override
    public void update(Note note, String title, String message, Callback<Note> callback) {

        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Note newNote = new Note(note.getId(), title, message, note.getCreatedAt());

                int index = data.indexOf(note);
                data.set(index, newNote);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(newNote);
                    }
                });
            }
        });

    }

}
