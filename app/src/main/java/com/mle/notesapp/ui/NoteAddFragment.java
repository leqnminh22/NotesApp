package com.mle.notesapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.mle.notesapp.R;

public class NoteAddFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_add, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MaterialToolbar toolbar = view.findViewById(R.id.toolbar);

        if (requireActivity() instanceof ToolbarHandler) {
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

        TextInputEditText editTitle = view.findViewById(R.id.add_note_title);
        TextInputEditText editDesc = view.findViewById(R.id.add_note_description);
    }
}
