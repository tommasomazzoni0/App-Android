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
 * Adapter per la visualizzazione di un elenco di assenze in un RecyclerView.
 * Gestisce la creazione e il binding degli item relativi alle assenze.
 */
public class assenzeAdapter extends RecyclerView.Adapter<assenzeAdapter.AssenzaViewHolder> {

    private List<Assenza> assenzeList;

    /**
     * Costruttore per l'adapter.
     * @param assenzeList Lista di oggetti Assenza da visualizzare nel RecyclerView.
     */
    public assenzeAdapter(List<Assenza> assenzeList) {
        this.assenzeList = assenzeList;
    }

    /**
     * Crea una nuova istanza di un item view.
     * Questo metodo è chiamato quando il RecyclerView ha bisogno di una nuova view.
     * @param parent Il gruppo di viste in cui la nuova vista sarà aggiunta.
     * @param viewType Il tipo di vista da creare (non utilizzato in questo caso).
     * @return Un nuovo ViewHolder contenente la vista dell'assenza.
     */
    @NonNull
    @Override
    public AssenzaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.assenze_item, parent, false);
        return new AssenzaViewHolder(view);
    }


    /**
     * Associa i dati dell'assenza all'item view.
     * Questo metodo è chiamato per ogni elemento visibile nel RecyclerView.
     *
     * @param holder Il ViewHolder in cui associare i dati.
     * @param position La posizione dell'elemento nella lista.
     */
    @Override
    public void onBindViewHolder(@NonNull AssenzaViewHolder holder, int position) {
        Assenza assenza = assenzeList.get(position);
        holder.dataTextView.setText("Data: " + assenza.getData());
    }

    /**
     * Restituisce il numero totale di elementi nella lista delle assenze.
     * @return Il numero totale di assenze.
     */
    @Override
    public int getItemCount() {
        return assenzeList.size();
    }

    /**
     * ViewHolder per l'item di un'assenza.
     * Contiene una vista di tipo TextView per visualizzare la data dell'assenza.
     */
    public static class AssenzaViewHolder extends RecyclerView.ViewHolder {

        TextView dataTextView;

        /**
         * Costruttore per il ViewHolder.
         * Associa la vista TextView al componente UI relativo alla data dell'assenza.
         *
         * @param itemView La vista dell'item a cui il ViewHolder è associato.
         */
        public AssenzaViewHolder(View itemView) {
            super(itemView);
            dataTextView = itemView.findViewById(R.id.dataassenza);
        }
    }
}
