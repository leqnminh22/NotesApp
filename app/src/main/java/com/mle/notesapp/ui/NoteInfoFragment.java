package com.mle.notesapp.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.MaterialToolbar;
import com.mle.notesapp.R;

public class NoteInfoFragment extends Fragment {

    NoteInfoFragment() {
        super(R.layout.fragment_note_info);
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
    }
}
