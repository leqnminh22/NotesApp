package com.mle.notesapp.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Note implements Parcelable {


    private String name;
    private String description;
    private Date date;
    private String id;


    public Note(String id, String name, String description, Date date) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
    }


    protected Note(Parcel in) {
        name = in.readString();
        description = in.readString();
        id = in.readString();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(id);
    }
}
