package com.example.app_registro_elettronico;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.app_registro_elettronico.gestione.*;

import java.util.List;


/**
 * Adapter per la visualizzazione delle note all'interno di un RecyclerView.
 * Collega i dati delle note alle rispettive viste per ogni elemento della lista.
 */
public class notesAdapter extends RecyclerView.Adapter<notesAdapter.NoteViewHolder> {

    private List<Note> noteList;

    /**
     * Costruttore per l'adapter, che riceve una lista di note.
     *
     * @param noteList Lista delle note da visualizzare.
     */
    public notesAdapter(List<Note> noteList) {
        this.noteList = noteList;
    }

    /**
     * Crea una nuova view per ogni item del RecyclerView.
     *
     * @param parent   Il gruppo di viste a cui la nuova vista appartiene.
     * @param viewType Il tipo di vista da creare (utile per la gestione di diverse viste).
     * @return Un nuovo oggetto NoteViewHolder che gestisce l'elemento della lista.
     */
    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new NoteViewHolder(view);
    }

    /**
     * Assegna i dati di una nota alla vista corrispondente.
     *
     * @param holder   Il ViewHolder che contiene le viste dell'elemento.
     * @param position La posizione dell'elemento nella lista.
     */
    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = noteList.get(position);
        holder.dateTextView.setText("Data: " + note.getstringData());
        holder.professorTextView.setText("Professore: " + note.getProfessor().getNome()+" "+note.getProfessor().getCognome());
        String noteInfo = note.getText();
        noteInfo = noteInfo.replace("Motivo_", "");
        holder.textTextView.setText("Motivazione: "+noteInfo);
    }

    /**
     * Restituisce il numero totale di elementi nella lista.
     * @return Il numero di elementi nella lista delle note.
     */
    @Override
    public int getItemCount() {
        return noteList.size();
    }

    /**
     * ViewHolder che rappresenta un singolo elemento della lista di note.
     * Gestisce la visualizzazione dei dati di ogni nota.
     */
    public static class NoteViewHolder extends RecyclerView.ViewHolder {

        TextView dateTextView;
        TextView professorTextView;
        TextView textTextView;

        /**
         * Costruttore del ViewHolder che inizializza le viste dell'item.
         * @param itemView La vista dell'item da cui si estraggono gli elementi.
         */
        public NoteViewHolder(View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.noteDate);
            professorTextView = itemView.findViewById(R.id.noteProfessor);
            textTextView = itemView.findViewById(R.id.noteText);
        }
    }
}
