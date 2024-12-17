package com.example.app_registro_elettronico;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.app_registro_elettronico.gestione.*;

import java.util.List;

public class assenzeAdapter extends RecyclerView.Adapter<assenzeAdapter.AssenzaViewHolder> {

    private List<Assenza> assenzeList;

    public assenzeAdapter(List<Assenza> assenzeList) {
        this.assenzeList = assenzeList;
    }

    @NonNull
    @Override
    public AssenzaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.assenze_item, parent, false);
        return new AssenzaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssenzaViewHolder holder, int position) {
        Assenza assenza = assenzeList.get(position);
        holder.dataTextView.setText("Data: " + assenza.getData());
    }

    @Override
    public int getItemCount() {
        return assenzeList.size();
    }

    public static class AssenzaViewHolder extends RecyclerView.ViewHolder {

        TextView dataTextView;

        public AssenzaViewHolder(View itemView) {
            super(itemView);
            dataTextView = itemView.findViewById(R.id.dataassenza);
        }
    }
}
