package com.example.app_registro_elettronico;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_registro_elettronico.gestione.*;
import com.example.app_registro_elettronico.valutazioneAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Activity per la gestione dell'inserimento delle valutazioni degli alunni.
 */
public class inserimentoValutazioniActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private valutazioneAdapter votiAdapter;
    private Button indietro, inserisci;
    private EditText dataEditText;
    private Spinner votoSpinner, materieSpinner;
    private float votoSelezionato = 0f;
    private String materiaSelezionata = "";
    private ArrayList<Classe> classi = new ArrayList<>();
    private Docente docente;
    private Classe classe;
    private Studente alunno;
    private ArrayList<Studente> studenti = new ArrayList<>();
    private Server server = new Server();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.valutazioni_activity);

        try {
            // Recupero dati dall'Intent
            Intent intent = getIntent();
            docente = (Docente) intent.getSerializableExtra("docente");
            classi = (ArrayList<Classe>) intent.getSerializableExtra("classi");
            classe = (Classe) intent.getSerializableExtra("classe");
            studenti = (ArrayList<Studente>) intent.getSerializableExtra("alunni");
            alunno = (Studente) intent.getSerializableExtra("alunno_selezionato");

            if (alunno == null) {
                throw new IllegalArgumentException("Dati dell'alunno mancanti");
            }

            // Inizializzazione dei componenti
            indietro = findViewById(R.id.indietro);
            inserisci = findViewById(R.id.inserisci);
            recyclerView = findViewById(R.id.votiRecyclerView);
            dataEditText = findViewById(R.id.data);
            materieSpinner = findViewById(R.id.SpinnerMateria);
            votoSpinner = findViewById(R.id.votoSpinner);

            // Configurazione RecyclerView
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            if (alunno.getVoti() == null) {
                alunno.setVoti(new ArrayList<>());
            }
            votiAdapter = new valutazioneAdapter(alunno.getVoti());
            recyclerView.setAdapter(votiAdapter);

            // Pulsante "Indietro"
            indietro.setOnClickListener(view -> {
                Intent intent1 = new Intent(inserimentoValutazioniActivity.this, alunniDocenteActivity.class);
                intent1.putExtra("classi", classi);
                intent1.putExtra("classe", classe);
                intent1.putExtra("alunni", studenti);
                intent1.putExtra("docente", docente);
                intent1.putExtra("alunno_selezionato", alunno);
                startActivity(intent1);
                finish();
            });

            // Configurazione del DatePicker
            dataEditText.setOnClickListener(view -> showDatePicker());

            // Configurazione dello Spinner per i voti
            ArrayAdapter<CharSequence> votoAdapter = ArrayAdapter.createFromResource(this,
                    R.array.voti_array, android.R.layout.simple_spinner_item);
            votoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            votoSpinner.setAdapter(votoAdapter);
            votoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    votoSelezionato = Float.parseFloat(parentView.getItemAtPosition(position).toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    votoSelezionato = 0f;
                }
            });

            // Configurazione dello Spinner per le materie
            if (docente != null && docente.getMaterie() != null) {
                ArrayAdapter<String> materieAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, docente.getMaterie());
                materieAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                materieSpinner.setAdapter(materieAdapter);
                materieSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        materiaSelezionata = adapterView.getItemAtPosition(i).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        materiaSelezionata = "";
                    }
                });
            }

            // Pulsante "Inserisci"
            inserisci.setOnClickListener(view -> inserisciValutazione());

        } catch (Exception e) {
            Log.e("inserimentoValutazioni", "Errore durante l'inizializzazione: " + e.getMessage(), e);
            Toast.makeText(this, "Errore nell'inizializzazione", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    /**
     * Mostra il DatePicker per selezionare una data.
     */
    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);

        int startYear, endYear;
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

        new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
            String selectedDate = String.format(Locale.getDefault(), "%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear);
            dataEditText.setText(selectedDate);
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    /**
     * Inserisce una valutazione per l'alunno selezionato.
     */
    private void inserisciValutazione() {
        // Ottieni il valore inserito per la data
        String dataInserita = dataEditText.getText().toString().trim();

        // Verifica che tutti i campi obbligatori siano compilati
        if (TextUtils.isEmpty(dataInserita) || votoSelezionato == 0 || TextUtils.isEmpty(materiaSelezionata)) {
            Toast.makeText(this, "Compila tutti i campi", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // Parsing della data inserita dall'utente
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            dateFormat.setLenient(false);
            Date data = dateFormat.parse(dataInserita);

            // Aggiungi la valutazione alla lista dell'alunno
            alunno.getVoti().add(new Voti(votoSelezionato, materiaSelezionata, docente, data));
            votiAdapter.notifyItemInserted(alunno.getVoti().size() - 1);

            // Resetta i campi di input
            dataEditText.setText("");
            votoSpinner.setSelection(0);
            materieSpinner.setSelection(0);

            // Mostra messaggio di conferma
            Toast.makeText(this, "Valutazione inserita con successo", Toast.LENGTH_SHORT).show();
            server.creaEliminaStudente("elimina", alunno);
            server.creaEliminaStudente("carica", alunno);

        } catch (ParseException e) {
            Toast.makeText(this, "Formato data non valido. Usa dd/MM/yyyy", Toast.LENGTH_SHORT).show();
        }
    }
}
