package com.mle.notesapp.domain;

public class Setting {
    private String name;
    private int icon;

    public Setting(String name, int icon) {
        this.name = name;
        this.icon = icon;
    }

    public int getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }


}
