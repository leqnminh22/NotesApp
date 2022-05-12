package com.mle.notesapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
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
import com.google.android.material.button.MaterialButton;
import com.mle.notesapp.R;
import com.mle.notesapp.domain.Callback;
import com.mle.notesapp.domain.InMemoryNoteRepository;
import com.mle.notesapp.domain.Note;

import java.util.List;

public class NoteListFragment extends Fragment {

    public static final String NOTES_CLICKED_KEY = "NOTES_CLICKED_KEY";
    public static final String SELECTED_NOTE = "SELECTED_NOTE";
    private ProgressBar progressBar;


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

        NotesAdapter adapter = new NotesAdapter();
        adapter.setNoteClicked(new NotesAdapter.OnNoteClicked() {
            @Override
            public void onNoteClicked(Note note) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, NoteDetailsFragment.newInstance(note))
                        .addToBackStack("")
                        .commit();
            }
        });
        notesList.setAdapter(adapter); // выставляем в recycler view

        progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        getParentFragmentManager()
                .setFragmentResultListener(AddNoteBottomSheetDialogFragment.KEY_RESULT, getViewLifecycleOwner(), new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                       Note note = result.getParcelable(AddNoteBottomSheetDialogFragment.ARG_NOTE);

                       int index = adapter.addNote(note);

                       adapter.notifyItemInserted(index);

                       notesList.smoothScrollToPosition(index);

                    }
                });

        view.findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AddNoteBottomSheetDialogFragment()
                        .show(getParentFragmentManager(), "AddNote");

            }
        });

        InMemoryNoteRepository.getInstance(requireContext()).getAll(new Callback<List<Note>>() {
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
}

