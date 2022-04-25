package com.mle.notesapp.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.MaterialToolbar;
import com.mle.notesapp.R;
import com.mle.notesapp.domain.InMemorySettingsRepository;
import com.mle.notesapp.domain.Setting;

import java.util.List;

public class NoteSettingsFragment extends Fragment {

    public NoteSettingsFragment() {
        super(R.layout.fragment_note_settings);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MaterialToolbar toolbar = view.findViewById(R.id.toolbar);

        if(requireActivity() instanceof ToolbarHandler) {
            ((ToolbarHandler) requireActivity()).setToolBar(toolbar);
        }

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.action_search:
                        Toast.makeText(requireContext(), "search", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.action_info:
                        Toast.makeText(requireContext(), "Info", Toast.LENGTH_SHORT).show();
                        return true;
                }
                return false;
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new NoteListFragment())
                        .commit();
            }
        });

        List<Setting> settings = InMemorySettingsRepository.getInstance(requireContext()).getAll();
        LinearLayout stsContainer = view.findViewById(R.id.settings_container);

        for (Setting setting : settings) {
            View itemSetting = getLayoutInflater().inflate(R.layout.item_settings, stsContainer, false);
            TextView settingName = itemSetting.findViewById(R.id.setting_name);
            settingName.setText(setting.getName());

            ImageView settingIcon = itemSetting.findViewById(R.id.setting_icon);
            settingIcon.setImageResource(setting.getIcon());

            stsContainer.addView(itemSetting);
        }


    }
}
