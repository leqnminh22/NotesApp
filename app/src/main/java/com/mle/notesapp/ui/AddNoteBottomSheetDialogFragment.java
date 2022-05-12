package com.mle.notesapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.mle.notesapp.R;
import com.mle.notesapp.domain.Callback;
import com.mle.notesapp.domain.InMemoryNoteRepository;
import com.mle.notesapp.domain.Note;

public class AddNoteBottomSheetDialogFragment extends BottomSheetDialogFragment {

    public static final String KEY_RESULT = "AddNoteBottomSheetDialogFragment_KEY_RESULT";
    public static final String ARG_NOTE = "ARG_NOTE";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_note_bottom_sheet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText title = view.findViewById(R.id.title);
        EditText message = view.findViewById(R.id.message);

        MaterialButton btnSave = view.findViewById(R.id.save);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btnSave.setEnabled(false);

                InMemoryNoteRepository.getInstance(requireContext()).add(title.getText().toString(), message.getText().toString(), new Callback<Note>() {
                    @Override
                    public void onSuccess(Note data) {

                        Bundle bundle = new Bundle();
                        bundle.putParcelable(ARG_NOTE, data);

                        getParentFragmentManager()
                                .setFragmentResult(KEY_RESULT, bundle);

                        btnSave.setEnabled(true);

                        dismiss();
                    }

                    @Override
                    public void onError(Throwable exception) {

                        btnSave.setEnabled(true);

                    }
                });



            }
        });
    }
}
