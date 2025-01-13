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
import java.util.Date;
import java.util.List;

/**
 * Activity per la gestione dell'inserimento delle note per gli alunni.
 * Permette di visualizzare una lista di note, aggiungere nuove note e navigare tra le schermate.
 */
public class inserimentoNoteActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private notesAdapter notesAdapter;
    private List<Note> noteList;
    private EditText dataEditText, nomeEditText, notaEditText;
    private Button indietro, inserisci;
    Docente docente;
    Classe classe;
    Studente alunno;

    ArrayList<Studente> alunni;

    /**
     * Inizializza l'activity, configura il RecyclerView e imposta gli ascoltatori di eventi.
     *
     * @param savedInstanceState Bundle che contiene lo stato precedentemente salvato dell'activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_activity);
        Intent intent = getIntent();
        docente = (Docente) intent.getSerializableExtra("docente");
        classe = (Classe) intent.getSerializableExtra("classi");
        alunni = (ArrayList<Studente>) intent.getSerializableExtra("alunni");
        alunno = (Studente) intent.getSerializableExtra("alunno_selezionato");

        indietro = findViewById(R.id.indietro);
        inserisci = findViewById(R.id.inserisci);
        dataEditText = findViewById(R.id.data);
        notaEditText = findViewById(R.id.nota);
        recyclerView = findViewById(R.id.notesRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        notesAdapter = new notesAdapter(alunno.getNote());
        recyclerView.setAdapter(notesAdapter);

        indietro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(inserimentoNoteActivity.this, alunniDocenteActivity.class);
                intent.putExtra("classe_nome", classe);
                intent.putExtra("alunni", alunni);
                startActivity(intent);
                finish();
            }
        });

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

        inserisci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date nuovaData = (Date) dataEditText.getText();
                String nuovaNota = notaEditText.getText().toString().trim();

                if (nuovaData == null && nuovaNota.isEmpty()) {
                    Toast.makeText(inserimentoNoteActivity.this, "Compila tutti i campi", Toast.LENGTH_SHORT).show();
                    return;
                }

                noteList.add(new Note(nuovaData, docente, nuovaNota));
                notesAdapter.notifyDataSetChanged();

                Toast.makeText(inserimentoNoteActivity.this, "Nota aggiunta con successo", Toast.LENGTH_SHORT).show();
                dataEditText.setText("");
                notaEditText.setText("");
            }
        });
    }
}
