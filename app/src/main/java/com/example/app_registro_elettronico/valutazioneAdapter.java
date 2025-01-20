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
 * Adapter per visualizzare una lista di valutazioni (voti) in un RecyclerView.
 * Ogni elemento nella lista contiene informazioni come la data, il voto (voto) e la descrizione.
 */
public class valutazioneAdapter extends RecyclerView.Adapter<valutazioneAdapter.ValutazioniViewHolder> {

    private List<Voti> votiList;


    /**
     * Costruttore per inizializzare l'adapter con la lista delle valutazioni (voti).
     *
     * @param votiList La lista delle valutazioni da visualizzare nel RecyclerView.
     */
    public valutazioneAdapter(List<Voti> votiList) {
        this.votiList = votiList;
    }

    /**
     * Chiamato quando viene creato un nuovo ViewHolder.
     * Questo metodo infla il layout per un singolo elemento di valutazione.
     *
     * @param parent   Il ViewGroup genitore che conterrà la vista dell'elemento.
     * @param viewType Il tipo di vista dell'elemento.
     * @return Un nuovo ViewHolder contenente la vista infusa dell'elemento.
     */
    @NonNull
    @Override
    public ValutazioniViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new ValutazioniViewHolder(view);
    }

    /**
     * Chiamato quando i dati di un elemento specifico della lista vengono associati al ViewHolder.
     * Imposta i dati (data, voto, descrizione) per ciascun elemento di valutazione.
     *
     * @param holder   Il ViewHolder al quale i dati devono essere associati.
     * @param position La posizione dell'elemento nella lista.
     */
    @Override
    public void onBindViewHolder(@NonNull ValutazioniViewHolder holder, int position) {
        Voti voto = votiList.get(position);
        holder.dataTextView.setText("Data: " + voto.getstringData());
        holder.votoTextView.setText("Voto: " + voto.getvoto());
        holder.descrizioneTextView.setText(voto.getdocente().getNome()+" "+voto.getdocente().getCognome());
    }

    /**
     * Restituisce il numero totale di elementi nella lista.
     *
     * @return Il numero di elementi nella lista dei voti.
     */
    @Override
    public int getItemCount() {
        return votiList.size();
    }

    /**
     * Classe ViewHolder per un singolo elemento di valutazione.
     * Contiene i riferimenti alle viste che visualizzano la data, il voto e la descrizione
     * per una valutazione specifica.
     */
    public static class ValutazioniViewHolder extends RecyclerView.ViewHolder {

        TextView dataTextView;
        TextView votoTextView;
        TextView descrizioneTextView;


        /**
         * Costruttore per inizializzare il ViewHolder con la vista dell'elemento.
         *
         * @param itemView La vista per un singolo elemento nel RecyclerView.
         */
        public ValutazioniViewHolder(View itemView) {
            super(itemView);
            dataTextView = itemView.findViewById(R.id.noteDate);
            votoTextView = itemView.findViewById(R.id.noteProfessor);
            descrizioneTextView = itemView.findViewById(R.id.noteText);
        }
    }
}
