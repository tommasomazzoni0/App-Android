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

public class inserimentoNoteActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private notesAdapter notesAdapter;
    private List<Note> noteList;
    private EditText dataEditText, nomeEditText, notaEditText;
    private Button indietro, inserisci;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_activity);

        indietro = findViewById(R.id.indietro);
        inserisci = findViewById(R.id.inserisci);
        dataEditText = findViewById(R.id.data); // Campo data
        nomeEditText = findViewById(R.id.nome); // Campo nome
        notaEditText = findViewById(R.id.nota); // Campo nota
        recyclerView = findViewById(R.id.notesRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Lista iniziale di note
        noteList = new ArrayList<>();
        noteList.add(new Note("01/01/2024", "Mario Rossi", "Nota di esempio 1"));
        noteList.add(new Note("02/01/2024", "Giovanni Bianchi", "Nota di esempio 2"));
        notesAdapter = new notesAdapter(noteList);
        recyclerView.setAdapter(notesAdapter);

        // Listener per pulsante "Indietro"
        indietro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(inserimentoNoteActivity.this, alunniDocenteActivity.class);
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
                        inserimentoNoteActivity.this,
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
                // Recupera i dati inseriti
                String nuovaData = dataEditText.getText().toString().trim();
                String nomeStudente = nomeEditText.getText().toString().trim();
                String nuovaNota = notaEditText.getText().toString().trim();

                if (nuovaData.isEmpty() || nomeStudente.isEmpty() || nuovaNota.isEmpty()) {
                    Toast.makeText(inserimentoNoteActivity.this, "Compila tutti i campi", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Aggiungi una nuova nota alla lista
                noteList.add(new Note(nuovaData, nomeStudente, nuovaNota));
                notesAdapter.notifyDataSetChanged(); // Aggiorna il RecyclerView

                // Mostra conferma
                Toast.makeText(inserimentoNoteActivity.this, "Nota aggiunta con successo", Toast.LENGTH_SHORT).show();

                // Pulisci i campi di input
                dataEditText.setText("");
                nomeEditText.setText("");
                notaEditText.setText("");
            }
        });
    }
}
