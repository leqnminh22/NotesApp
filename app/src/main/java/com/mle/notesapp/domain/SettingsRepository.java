package com.mle.notesapp.domain;

import java.util.List;

public interface SettingsRepository {
    List<Setting> getAll();
    void add (Setting setting);
}
