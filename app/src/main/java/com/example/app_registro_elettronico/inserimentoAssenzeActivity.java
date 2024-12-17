package com.example.app_registro_elettronico;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.app_registro_elettronico.gestione.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class inserimentoAssenzeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private assenzeAdapter assenzaAdapter;
    private List<Assenza> assenzeList;
    private EditText dataEditText; // Campo data
    private Button indietro, inserisci;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assenze_activity);

        indietro = findViewById(R.id.indietro);
        inserisci = findViewById(R.id.inserisci);
        dataEditText = findViewById(R.id.data); // Associa il campo EditText
        recyclerView = findViewById(R.id.assenzeRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Lista iniziale di assenze
        assenzeList = new ArrayList<>();
        assenzeList.add(new Assenza("01/01/2024", false, "dasdasd"));
        assenzeList.add(new Assenza("02/01/2024", false, "dadsa"));
        assenzeList.add(new Assenza("02/01/2024", true, "Ndasdasda"));
        assenzeList.add(new Assenza("02/01/2024", true, "dasdsadas"));
        assenzaAdapter = new assenzeAdapter(assenzeList);
        recyclerView.setAdapter(assenzaAdapter);

        // Listener per pulsante "Indietro"
        indietro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(inserimentoAssenzeActivity.this, alunniDocenteActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Listener per EditText della data
        dataEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Ottieni data corrente
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // Mostra il DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        inserimentoAssenzeActivity.this,
                        (datePicker, selectedYear, selectedMonth, selectedDay) -> {
                            // Imposta la data selezionata nell'EditText
                            String selectedDate = String.format("%02d/%02d/%d", selectedDay, selectedMonth + 1, selectedYear);
                            dataEditText.setText(selectedDate);
                        },
                        year, month, day
                );
                datePickerDialog.show();
            }
        });

        // Listener per pulsante "Inserisci"
        inserisci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Recupera la data inserita
                String nuovaData = dataEditText.getText().toString().trim();

                if (nuovaData.isEmpty()) {
                    Toast.makeText(inserimentoAssenzeActivity.this, "Inserisci una data valida", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Aggiungi una nuova assenza con una nota vuota di default
                assenzeList.add(new Assenza(nuovaData, false, "Nuova assenza"));
                assenzaAdapter.notifyDataSetChanged(); // Aggiorna l'RecyclerView

                // Mostra conferma
                Toast.makeText(inserimentoAssenzeActivity.this, "Assenza aggiunta con successo", Toast.LENGTH_SHORT).show();

                // Pulisci il campo data
                dataEditText.setText("");
            }
        });
    }
}
