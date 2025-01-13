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

import com.example.app_registro_elettronico.gestione.Voti;
import com.example.app_registro_elettronico.valutazioneAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Activity per la gestione dell'inserimento delle valutazioni degli alunni.
 * Consente di visualizzare le valutazioni, aggiungerne di nuove e navigare tra le schermate.
 */
public class inserimentoValutazioniActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private valutazioneAdapter votiAdapter;
    private List<Voti> votiList;
    private Button indietro, inserisci;
    private EditText dataEditText, descrizioneEditText;
    private Spinner votoSpinner;
    private float votoSelezionato;

    /**
     * Inizializza l'activity, configura il RecyclerView, il spinner per i voti e imposta gli ascoltatori di eventi.
     *
     * @param savedInstanceState Bundle che contiene lo stato precedentemente salvato dell'activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.valutazioni_activity);
        String classe = getIntent().getStringExtra("classe_nome");
        ArrayList<String> alunni = getIntent().getStringArrayListExtra("alunni");


        indietro = findViewById(R.id.indietro);
        inserisci = findViewById(R.id.inserisci);
        recyclerView = findViewById(R.id.votiRecyclerView);
        dataEditText = findViewById(R.id.data);
        descrizioneEditText = findViewById(R.id.Descrizione);
        votoSpinner = findViewById(R.id.votoSpinner);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        votiList = new ArrayList<>();
        votiAdapter = new valutazioneAdapter(votiList);
        recyclerView.setAdapter(votiAdapter);

        votiList.add(new Voti( 3, "matematica","SUSS","2024/01/02"));
        votiList.add(new Voti("02/01/2024", 10, "Nota di esempio 2"));
        votiAdapter.notifyDataSetChanged();

        indietro.setOnClickListener(view -> {
            Intent intent = new Intent(inserimentoValutazioniActivity.this, alunniDocenteActivity.class);
            intent.putExtra("classe_nome", classe);
            intent.putStringArrayListExtra("alunni", alunni);
            startActivity(intent);
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
            String data = dataEditText.getText().toString();
            String docente = descrizioneEditText.getText().toString();

            if (TextUtils.isEmpty(data) || votoSelezionato == 0 || TextUtils.isEmpty(docente)) {
                Toast.makeText(this, "Compila tutti i campi", Toast.LENGTH_SHORT).show();
                return;
            }

            votiList.add(new Voti(votoSelezionato, materia, docente,data));
            votiAdapter.notifyItemInserted(votiList.size() - 1);

            dataEditText.setText("");
            descrizioneEditText.setText("");
            votoSpinner.setSelection(0);
            Toast.makeText(this, "Valutazione inserita con successo", Toast.LENGTH_SHORT).show();
        });
    }
}
