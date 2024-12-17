package com.example.app_registro_elettronico;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.app_registro_elettronico.gestione.*;

import java.util.List;

public class valutazioneAdapter extends RecyclerView.Adapter<valutazioneAdapter.ValutazioniViewHolder> {

    private List<Voti> votiList;

    public valutazioneAdapter(List<Voti> votiList) {
        this.votiList = votiList;
    }

    @NonNull
    @Override
    public ValutazioniViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new ValutazioniViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ValutazioniViewHolder holder, int position) {
        Voti voto = votiList.get(position);
        holder.dataTextView.setText("Data: " + voto.getdata());
        holder.votoTextView.setText("Voto: " + voto.getvoto());
        holder.descrizioneTextView.setText(voto.getdescrizione());
    }

    @Override
    public int getItemCount() {
        return votiList.size();
    }

    public static class ValutazioniViewHolder extends RecyclerView.ViewHolder {

        TextView dataTextView;
        TextView votoTextView;
        TextView descrizioneTextView;

        public ValutazioniViewHolder(View itemView) {
            super(itemView);
            dataTextView = itemView.findViewById(R.id.noteDate);
            votoTextView = itemView.findViewById(R.id.noteProfessor);
            descrizioneTextView = itemView.findViewById(R.id.noteText);
        }
    }
}
