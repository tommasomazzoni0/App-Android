package com.example.app_registro_elettronico;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.app_registro_elettronico.gestione.*;

import java.text.ParseException;
import java.util.*;

/**
 * Activity per la gestione dell'inserimento delle assenze per gli alunni.
 * Consente di visualizzare una lista di assenze, inserire nuove assenze.
 */
public class inserimentoAssenzeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private assenzeAdapter assenzaAdapter;
    private EditText dataEditText;
    private Button indietro, inserisci;
    private TextView assenzeTextView; // TextView per indicare assenza di assenze
    private Docente docente;
    private Studente alunno;
    private ArrayList<Studente> alunni = new ArrayList<>();
    private ArrayList<Classe> classi = new ArrayList<>();
    private Classe classe;
    private Server server = new Server();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assenze_activity);

        // Recupera i dati dall'Intent
        Intent intent = getIntent();
        docente = (Docente) intent.getSerializableExtra("docente");
        classi = (ArrayList<Classe>) intent.getSerializableExtra("classi");
        classe = (Classe) intent.getSerializableExtra("classe");
        alunni = (ArrayList<Studente>) intent.getSerializableExtra("alunni");
        alunno = (Studente) intent.getSerializableExtra("alunno_selezionato");

        // Inizializzazione delle view
        indietro = findViewById(R.id.indietro);
        inserisci = findViewById(R.id.inserisci);
        dataEditText = findViewById(R.id.data);
        recyclerView = findViewById(R.id.assenzeRecyclerView);
        assenzeTextView = findViewById(R.id.assenzeTextView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        assenzaAdapter = new assenzeAdapter(alunno.getAssenze());
        recyclerView.setAdapter(assenzaAdapter);

        // Gestione visibilità della TextView per l'assenza di assenze
        toggleEmptyView();

        // Listener per il pulsante "Indietro"
        indietro.setOnClickListener(view -> {
            Intent backIntent = new Intent(inserimentoAssenzeActivity.this, alunniDocenteActivity.class);
            backIntent.putExtra("classi", classi);
            backIntent.putExtra("classe", classe);
            backIntent.putExtra("alunni", alunni);
            backIntent.putExtra("docente", docente);
            backIntent.putExtra("alunno_selezionato", alunno);

            startActivity(backIntent);
            finish();
        });

        // Listener per la scelta della data
        dataEditText.setOnClickListener(view -> {
            final Calendar calendar = Calendar.getInstance();
            int currentYear = calendar.get(Calendar.YEAR);
            int currentMonth = calendar.get(Calendar.MONTH);

            int startYear;
            int endYear;

            if (currentMonth >= Calendar.SEPTEMBER) {
                startYear = currentYear;
                endYear = currentYear + 1;
            } else {
                startYear = currentYear - 1;
                endYear = currentYear;
            }

            Calendar startDate = Calendar.getInstance();
            startDate.set(startYear, Calendar.SEPTEMBER, 1);

            Calendar endDate = Calendar.getInstance();
            endDate.set(endYear, Calendar.JUNE, 30);

            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view1, selectedYear, selectedMonth, selectedDay) -> {
                String selectedDate = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear);
                dataEditText.setText(selectedDate);
            }, year, month, day);

            datePickerDialog.getDatePicker().setMinDate(startDate.getTimeInMillis());
            datePickerDialog.getDatePicker().setMaxDate(endDate.getTimeInMillis());

            datePickerDialog.show();
        });

        // Listener per il pulsante "Inserisci"
        inserisci.setOnClickListener(view -> {
            String dataText = dataEditText.getText().toString();

            if (dataText.isEmpty()) {
                Toast.makeText(inserimentoAssenzeActivity.this, "Inserisci una data valida", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                dateFormat.setLenient(false);
                Date nuovaData = dateFormat.parse(dataText);
                alunno.getAssenze().add(new Assenza(docente, nuovaData, false));
                assenzaAdapter.notifyDataSetChanged();

                Toast.makeText(inserimentoAssenzeActivity.this, "Assenza aggiunta con successo", Toast.LENGTH_SHORT).show();

                server.creaEliminaStudente("elimina", alunno);
                server.creaEliminaStudente("carica", alunno);

                dataEditText.setText("");

                // Aggiorna la visibilità della TextView
                toggleEmptyView();

            } catch (ParseException e) {
                Toast.makeText(inserimentoAssenzeActivity.this, "Formato data non valido. Usa dd/MM/yyyy", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Mostra o nasconde la TextView in base alla presenza di assenze.
     */
    private void toggleEmptyView() {
        if (alunno.getAssenze().isEmpty()) {
            assenzeTextView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            assenzeTextView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
}
