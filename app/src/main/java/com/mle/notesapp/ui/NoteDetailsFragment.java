package com.mle.notesapp.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.google.android.material.appbar.MaterialToolbar;
import com.mle.notesapp.R;
import com.mle.notesapp.domain.Note;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class NoteDetailsFragment extends Fragment {

    private static final String ARG_NOTE = "ARG_NOTE";
    private TextView title;
    private TextView date;
    private TextView description;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM, HH:mm", Locale.getDefault());

    public NoteDetailsFragment() {
        super(R.layout.fragment_note_details);
    }

    public static NoteDetailsFragment newInstance(Note note) {

        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, note);

        NoteDetailsFragment fragment = new NoteDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        title = view.findViewById(R.id.note_title);
        date = view.findViewById(R.id.note_date);
        description = view.findViewById(R.id.note_description);

        MaterialToolbar toolbar = view.findViewById(R.id.toolbar);

        if(requireActivity() instanceof ToolbarHandler) {
            ((ToolbarHandler) requireActivity()).setToolBar(toolbar);
        }

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.action_share:
                        Toast.makeText(requireContext(), "share", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.action_photo:
                        Toast.makeText(requireContext(), "add photo", Toast.LENGTH_SHORT).show();
                        return true;
                }
                return false;
            }
        });
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getParentFragmentManager()
//                        .popBackStack();
//            }
//        });

        getParentFragmentManager().setFragmentResultListener(NoteListFragment.NOTES_CLICKED_KEY, getViewLifecycleOwner(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                Note note = result.getParcelable(NoteListFragment.SELECTED_NOTE);
                showNote(note);
            }
        });

        if (getArguments() != null && getArguments().containsKey(ARG_NOTE)) {
            Note note = getArguments().getParcelable(ARG_NOTE);
            showNote(note);
        }
    }

    public void showNote(Note note) {
        title.setText(note.getName());
        description.setText(note.getDescription());
        date.setText(simpleDateFormat.format(note.getDate()));
    }
}
