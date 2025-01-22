package com.example.app_registro_elettronico;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.app_registro_elettronico.gestione.Classe;
import com.example.app_registro_elettronico.gestione.Docente;
import com.example.app_registro_elettronico.gestione.Genitore;
import com.example.app_registro_elettronico.gestione.Studente;

import java.util.ArrayList;

/**
 * La classe alunniDocenteActivity rappresenta un'attività che consente ai docenti di gestire
 * le valutazioni, le note e le assenze degli alunni di una determinata classe.
 * Fornisce opzioni per navigare verso attività specifiche per l'inserimento di queste informazioni.
 */
public class alunniDocenteActivity extends AppCompatActivity {

    TextView valutazioniTextView, noteTextView, assenzeTextView;
    Button logout, indietro;
    ArrayList<Classe> classe = new ArrayList<>();
    ArrayList<Studente> alunni= new ArrayList<>();
    Docente docente;
    Studente alunno;

    /**
     * Metodo chiamato durante la creazione dell'attività.
     * Inizializza la vista, configura i listener per i pulsanti e gestisce i dati passati tramite intent.
     *
     * @param savedInstanceState Lo stato salvato dell'attività, se presente.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docente);
        Intent intent = getIntent();
        docente = (Docente) intent.getSerializableExtra("docente");
        classe = (ArrayList<Classe>) intent.getSerializableExtra("classi");
        alunni = (ArrayList<Studente>) intent.getSerializableExtra("alunni");
        alunno = (Studente) intent.getSerializableExtra("alunno_selezionato");


        valutazioniTextView = findViewById(R.id.valutazioni);
        noteTextView = findViewById(R.id.note);
        assenzeTextView = findViewById(R.id.assenze);

        valutazioniTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(alunniDocenteActivity.this, inserimentoValutazioniActivity.class);
                intent.putExtra("classi", classe);
                intent.putExtra("alunno_selezionato", alunno);
                startActivity(intent);
                finish();
            }
        });

        noteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(alunniDocenteActivity.this, inserimentoNoteActivity.class);
                intent.putExtra("classi", classe);
                intent.putExtra("alunno_selezionato", alunno);
                startActivity(intent);
                finish();
            }
        });

        assenzeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(alunniDocenteActivity.this, inserimentoAssenzeActivity.class);
                intent.putExtra("classi", classe);
                intent.putExtra("alunno_selezionato", alunno);
                startActivity(intent);
                finish();
            }
        });

        indietro = findViewById(R.id.indietro);
        indietro.setOnClickListener(v -> {
            Intent intent1 = new Intent(alunniDocenteActivity.this, alunniActivity.class);
            intent1.putExtra("classi", classe);
            intent1.putExtra("alunni", alunni);
            startActivity(intent1);
            finish();
        });


        logout = findViewById(R.id.logOutButton);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(alunniDocenteActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }


}
