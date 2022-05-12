package com.mle.notesapp.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.mle.notesapp.R;
import com.mle.notesapp.domain.Note;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private Fragment fragment;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM, HH:mm", Locale.getDefault());

    private OnNoteClicked noteClicked;

    private List<Note> data = new ArrayList<>();

    public void setData(Collection<Note> notes) {
        data.addAll(notes);
    }

    public void removeNote(Note selectedNote) {
        data.remove(selectedNote);
    }

    public void replaceNote(Note note, int selectedPosition) {
        data.set(selectedPosition, note);
    }

    interface OnNoteClicked {
        void onNoteClicked(Note note);

        void onNoteLongClicked(Note note, int position);
    }

    public OnNoteClicked getNoteClicked() {
        return noteClicked;
    }

    public void setNoteClicked(OnNoteClicked noteClicked) {
        this.noteClicked = noteClicked;
    }

    public NotesAdapter(Fragment fragment) {
        this.fragment = fragment;
    }


    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        NotesViewHolder holder = new NotesViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        Note note = data.get(position);

        holder.title.setText(note.getName());
        holder.description.setText(note.getDescription());
        holder.date.setText(simpleDateFormat.format(note.getDate()));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public int addNote(Note note) {
        data.add(note);
        return data.size() -1;
    }



    class NotesViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;
        TextView date;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.note_title);
            description = itemView.findViewById(R.id.note_description);
            date = itemView.findViewById(R.id.note_date);

            CardView cardView = itemView.findViewById(R.id.root);

            fragment.registerForContextMenu(cardView);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(noteClicked != null) {
                        int clickedPosition = getAdapterPosition();
                        noteClicked.onNoteClicked(data.get(clickedPosition));
                    }
                }
            });

            cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    cardView.showContextMenu();

                    if(noteClicked != null) {
                        int clickedPosition = getAdapterPosition();
                        noteClicked.onNoteLongClicked(data.get(clickedPosition), clickedPosition);
                    }

                    return true;
                }
            });
        }
    }
}
