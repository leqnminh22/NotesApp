package com.mle.notesapp.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mle.notesapp.R;
import com.mle.notesapp.domain.InMemoryNoteRepository;
import com.mle.notesapp.domain.Note;

import java.util.List;

public class NoteListFragment extends Fragment {

    public static final String NOTES_CLICKED_KEY = "NOTES_CLICKED_KEY";
    public static final String SELECTED_NOTE = "SELECTED_NOTE";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<Note> notes = InMemoryNoteRepository.getInstance(requireContext()).getAll();

        LinearLayout container = view.findViewById(R.id.container);

        for (Note note : notes) {
            View itemView = getLayoutInflater().inflate(R.layout.item_note, container, false);

            itemView.findViewById(R.id.root).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(SELECTED_NOTE, note);
                        getParentFragmentManager()
                                .setFragmentResult(NOTES_CLICKED_KEY, bundle);
                    } else {
                        NoteDetailsActivity.show(requireContext(), note);

                    }

                }
            });

            TextView title = itemView.findViewById(R.id.note_title);
            title.setText(note.getName());

            TextView date = itemView.findViewById(R.id.note_date);
            date.setText(note.getDate());

            container.addView(itemView);


        }
    }
}
