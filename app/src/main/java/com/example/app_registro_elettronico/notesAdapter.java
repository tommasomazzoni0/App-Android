package com.example.app_registro_elettronico;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.app_registro_elettronico.gestione.*;

import java.util.List;

public class notesAdapter extends RecyclerView.Adapter<notesAdapter.NoteViewHolder> {

    private List<Note> noteList;

    public notesAdapter(List<Note> noteList) {
        this.noteList = noteList;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = noteList.get(position);
        holder.dateTextView.setText("Data: " + note.getDate());
        holder.professorTextView.setText("Professore: " + note.getProfessor());
        holder.textTextView.setText(note.getText());
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder {

        TextView dateTextView;
        TextView professorTextView;
        TextView textTextView;

        public NoteViewHolder(View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.noteDate);
            professorTextView = itemView.findViewById(R.id.noteProfessor);
            textTextView = itemView.findViewById(R.id.noteText);
        }
    }
}
