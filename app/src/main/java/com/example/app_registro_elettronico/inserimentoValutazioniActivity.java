package com.example.app_registro_elettronico;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Activity per la gestione dell'inserimento delle valutazioni degli alunni.
 * Consente di visualizzare le valutazioni, aggiungerne di nuove e navigare tra le schermate.
 */
public class inserimentoValutazioniActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private valutazioneAdapter votiAdapter;
    private Button indietro, inserisci;
    private EditText dataEditText, descrizioneEditText;
    private Spinner votoSpinner;
    private float votoSelezionato;
    ArrayList<Classe> classe = new ArrayList<>();
    Docente docente;
    Studente alunno;
    ArrayList<Studente> alunni = new ArrayList<>();
    ArrayList<Studente> studenti = new ArrayList<>();

    /**
     * Inizializza l'activity, configura il RecyclerView, il spinner per i voti e imposta gli ascoltatori di eventi.
     *
     * @param savedInstanceState Bundle che contiene lo stato precedentemente salvato dell'activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.valutazioni_activity);
        Intent intent = getIntent();
        docente = (Docente) intent.getSerializableExtra("docente");
        classe = (ArrayList<Classe>) intent.getSerializableExtra("classi");
        alunni = (ArrayList<Studente>) intent.getSerializableExtra("alunni");
        alunno = (Studente) intent.getSerializableExtra("alunno_selezionato");

        indietro = findViewById(R.id.indietro);
        inserisci = findViewById(R.id.inserisci);
        recyclerView = findViewById(R.id.votiRecyclerView);
        dataEditText = findViewById(R.id.data);
        descrizioneEditText = findViewById(R.id.Descrizione);
        votoSpinner = findViewById(R.id.votoSpinner);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        votiAdapter = new valutazioneAdapter(alunno.getVoti());
        recyclerView.setAdapter(votiAdapter);
        votiAdapter.notifyDataSetChanged();

        indietro.setOnClickListener(view -> {
            Intent intent1 = new Intent(inserimentoValutazioniActivity.this, alunniDocenteActivity.class);
            intent1.putExtra("classi", classe);
            intent1.putExtra("alunni", studenti);
            startActivity(intent1);
            finish();
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


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.voti_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        votoSpinner.setAdapter(adapter);

        votoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                votoSelezionato = Float.parseFloat(parentView.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                votoSelezionato = 0;
            }
        });

        inserisci.setOnClickListener(view -> {
            Date data = (Date) dataEditText.getText();

            if (TextUtils.isEmpty((CharSequence) data) || votoSelezionato == 0) {
                Toast.makeText(this, "Compila tutti i campi", Toast.LENGTH_SHORT).show();
                return;
            }


            alunno.getVoti().add(new Voti(votoSelezionato,"italiano", docente,data));
            votiAdapter.notifyItemInserted(alunno.getVoti().size() - 1);

            dataEditText.setText("");
            descrizioneEditText.setText("");
            votoSpinner.setSelection(0);
            Toast.makeText(this, "Valutazione inserita con successo", Toast.LENGTH_SHORT).show();
        });
    }
}
