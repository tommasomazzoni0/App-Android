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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Activity per la gestione dell'inserimento delle note per gli alunni.
 * Permette di visualizzare una lista di note, aggiungere nuove note.
 */
public class inserimentoNoteActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private notesAdapter notesAdapter;
    private ArrayList<Note> noteList;
    private EditText dataEditText, notaEditText;
    private Button indietro, inserisci;
    private Docente docente;
    private ArrayList<Classe> classi = new ArrayList<>();
    private Studente alunno;
    private Classe classe;
    private ArrayList<Studente> alunni;
    private Server server= new Server();

    /**
     * Inizializza l'activity.
     *
     * @param savedInstanceState Bundle che contiene lo stato precedentemente salvato dell'activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_activity);

        // Recupera i dati passati tramite Intent
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
        notaEditText = findViewById(R.id.nota);
        recyclerView = findViewById(R.id.notesRecyclerView);

        // Inizializza la lista delle note
        noteList = alunno.getNote();
        if (noteList == null) {
            noteList = new ArrayList<>(); // Se la lista è null, inizializzala
            alunno.setNote(noteList); // Associa la nuova lista all'alunno
        }

        // Configura il RecyclerView e l'adattatore
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        notesAdapter = new notesAdapter(noteList);
        recyclerView.setAdapter(notesAdapter);

        // Listener per il pulsante "Indietro"
        indietro.setOnClickListener(view -> {
            Intent backIntent = new Intent(inserimentoNoteActivity.this, alunniDocenteActivity.class);
            backIntent.putExtra("classi", classi);
            backIntent.putExtra("classe", classe);
            backIntent.putExtra("alunni", alunni);
            backIntent.putExtra("docente", docente);
            backIntent.putExtra("alunno_selezionato", alunno);
            startActivity(backIntent);
            finish();
        });

        // Listener per il campo data
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
            String dataText = dataEditText.getText().toString().trim();
            String nuovaNota = notaEditText.getText().toString().trim();

            if (dataText.isEmpty() || nuovaNota.isEmpty()) {
                Toast.makeText(inserimentoNoteActivity.this, "Compila tutti i campi", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                // Parsing della data
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                dateFormat.setLenient(false); // Imposta il parser per rifiutare date non valide
                Date nuovaData = dateFormat.parse(dataText);

                // Aggiungi la nota alla lista
                noteList.add(new Note(nuovaData, docente, nuovaNota));
                notesAdapter.notifyDataSetChanged();

                Toast.makeText(inserimentoNoteActivity.this, "Nota aggiunta con successo", Toast.LENGTH_SHORT).show();
                server.creaEliminaStudente("elimina",alunno);
                server.creaEliminaStudente("carica",alunno);


                // Resetta i campi di input
                dataEditText.setText("");
                notaEditText.setText("");

            } catch (ParseException e) {
                Toast.makeText(inserimentoNoteActivity.this, "Formato data non valido. Usa dd/MM/yyyy", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
