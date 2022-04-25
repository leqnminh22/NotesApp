package com.mle.notesapp.domain;

import android.content.Context;

import com.mle.notesapp.R;

import java.util.ArrayList;
import java.util.List;

public class InMemorySettingsRepository implements SettingsRepository{
    private static InMemorySettingsRepository INSTANCE;
    private Context context;

    private InMemorySettingsRepository(Context context) {
        this.context = context;
    }

    public static InMemorySettingsRepository getInstance(Context context) {
        if(INSTANCE == null) {
            INSTANCE = new InMemorySettingsRepository(context);
        }
        return INSTANCE;
    }


    @Override
    public List<Setting> getAll() {
        ArrayList<Setting> settings = new ArrayList<>();
        settings.add(new Setting(context.getString(R.string.font_size), R.drawable.ic_baseline_font_download_24));
        settings.add(new Setting(context.getString(R.string.language), R.drawable.ic_baseline_language_24));
        return settings;
    }

    @Override
    public void add(Setting setting) {

    }
}
