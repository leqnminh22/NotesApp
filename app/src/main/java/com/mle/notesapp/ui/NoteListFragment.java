package com.mle.notesapp.ui;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.mle.notesapp.R;
import com.mle.notesapp.dependency.Dependencies;
import com.mle.notesapp.domain.Callback;
import com.mle.notesapp.domain.Note;

import java.util.List;

public class NoteListFragment extends Fragment {

    public static final String NOTES_CLICKED_KEY = "NOTES_CLICKED_KEY";
    public static final String SELECTED_NOTE = "SELECTED_NOTE";
    private ProgressBar progressBar;

    private NotesAdapter adapter;

    private Note selectedNote;
    private int selectedPosition;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getParentFragmentManager()
                .setFragmentResultListener(CloseDialogFragment.RESULT_KEY, getViewLifecycleOwner(), new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        String message = result.getString(CloseDialogFragment.MESSAGE);

                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                    }
                });


        MaterialToolbar toolbar = view.findViewById(R.id.toolbar);

        if (requireActivity() instanceof ToolbarHandler) {
            ((ToolbarHandler) requireActivity()).setToolBar(toolbar);
        }

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.action_info:
                        Toast.makeText(requireContext(), "info", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.action_search:
                        Toast.makeText(requireContext(), "search", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.add_note:
                        getParentFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, new NoteAddFragment())
                                .commit();
                        return true;
                }
                return false;
            }
        });


//        MaterialButton btnClose = view.findViewById(R.id.btnClose);
//        btnClose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                CloseDialogFragment.newInstance("Type \"Close\" to confirm closing app")
//                        .show(getParentFragmentManager(), "");
//
//            }
//        });

        RecyclerView notesList = view.findViewById(R.id.notes_list);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        notesList.setLayoutManager(layoutManager);

        adapter = new NotesAdapter(this);
        adapter.setNoteClicked(new NotesAdapter.OnNoteClicked() {
            @Override
            public void onNoteClicked(Note note) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, NoteDetailsFragment.newInstance(note))
                        .addToBackStack("")
                        .commit();
            }

            @Override
            public void onNoteLongClicked(Note note, int position) {

                selectedNote = note;
                selectedPosition = position;

            }
        });
        notesList.setAdapter(adapter); // выставляем в recycler view

        progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        getParentFragmentManager()
                .setFragmentResultListener(AddNoteBottomSheetDialogFragment.ADD_KEY_RESULT, getViewLifecycleOwner(), new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        Note note = result.getParcelable(AddNoteBottomSheetDialogFragment.ARG_NOTE);

                        int index = adapter.addNote(note);

                        adapter.notifyItemInserted(index);

                        notesList.smoothScrollToPosition(index);

                    }
                });

        getParentFragmentManager().setFragmentResultListener(AddNoteBottomSheetDialogFragment.UPDATE_KEY_RESULT, getViewLifecycleOwner(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                Note note = result.getParcelable(AddNoteBottomSheetDialogFragment.ARG_NOTE);

                adapter.replaceNote(note, selectedPosition);

                adapter.notifyItemChanged(selectedPosition);
            }
        });

        view.findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AddNoteBottomSheetDialogFragment.addInstance()
                        .show(getParentFragmentManager(), "AddNote");

            }
        });

        Dependencies.getNotesRepository().getAll(new Callback<List<Note>>() {
            @Override
            public void onSuccess(List<Note> data) {
                adapter.setData(data); // передаем список заметок адаптеру
                adapter.notifyDataSetChanged(); // Recycler view перерисовывает список

                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Throwable exception) {

                progressBar.setVisibility(View.GONE);

            }
        });
    }


    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater menuInflater = requireActivity().getMenuInflater();
        menuInflater.inflate(R.menu.menu_notes_context, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_edit:

                AddNoteBottomSheetDialogFragment.editInstance(selectedNote)
                        .show(getParentFragmentManager(), "EditNote");

                return true;

            case R.id.action_delete:

                progressBar.setVisibility(View.VISIBLE);

                Dependencies.getNotesRepository().remove(selectedNote, new Callback<Void>() {
                    @Override
                    public void onSuccess(Void data) {
                        progressBar.setVisibility(View.GONE);
                        adapter.removeNote(selectedNote);
                        adapter.notifyItemRemoved(selectedPosition);
                    }

                    @Override
                    public void onError(Throwable exception) {

                    }
                });
                return true;
        }
        return super.onContextItemSelected(item);
    }
}

