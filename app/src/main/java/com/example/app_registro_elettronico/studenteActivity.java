package com.example.app_registro_elettronico;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.app_registro_elettronico.gestione.Assenza;
import com.example.app_registro_elettronico.gestione.Studente;

import java.util.ArrayList;

/**
 * Activity che gestisce l'interfaccia studente, visualizzando i dettagli come voti, note e assenze.
 */
public class studenteActivity extends AppCompatActivity {

    private RelativeLayout cerchioVerde;
    private TextView numeroTextView, materiaTextView, noteTextView, assenzeTextView, titoloValutazioni, nomeMateria;
    private LinearLayout materieLayout, votiLayout, noteLayout, assenzeLayout;
    private Button logout, buttonMaterieIndietro, buttonVotiIndietro, buttonNoteIndietro, buttonAssenzaIndietro;
    private ArrayList<String> materie;
    Studente studente;

    /**
     * Metodo di creazione dell'Activity. Viene inizializzata l'interfaccia utente e i dati.
     *
     * @param savedInstanceState Stato precedente dell'Activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studente);
        Intent intent = getIntent();
        studente = (Studente) intent.getSerializableExtra("studente");

        cerchioVerde = findViewById(R.id.cerchio_verde);
        numeroTextView = findViewById(R.id.numero);
        materiaTextView = findViewById(R.id.media);
        noteTextView = findViewById(R.id.note);
        assenzeTextView = findViewById(R.id.assenze);
        materieLayout = findViewById(R.id.materieLayout);
        titoloValutazioni = findViewById(R.id.titoloValutazioni);
        votiLayout = findViewById(R.id.votiLayout);
        noteLayout =findViewById(R.id.noteElenco);
        assenzeLayout = findViewById(R.id.assenzeElenco);
        nomeMateria = findViewById(R.id.titoloMateria);
        logout = findViewById(R.id.logOutButton);
        buttonMaterieIndietro = findViewById(R.id.backButtonMaterie);
        buttonAssenzaIndietro = findViewById(R.id.backButtonAssenze);
        buttonVotiIndietro = findViewById(R.id.backButtonVoti);
        buttonNoteIndietro = findViewById(R.id.backButtonNote);

        double media = studente.getmedia();
        ArrayList<String> materie= new ArrayList<>();
        materie.add("Matematica");
        materie.add("Italiano");
        materie.add("Storia");
        materie.add("Informatica");
        materie.add("Sistemi");


        numeroTextView.setText(String.valueOf(media));

        if (media >= 6) {
            cerchioVerde.setBackgroundResource(R.drawable.cerchio_verde);
        } else {
            cerchioVerde.setBackgroundResource(R.drawable.cerchio_rosso);
        }

        TextView valutazioniTextView = findViewById(R.id.valutazioni);
        TextView votiTextView = findViewById(R.id.titoloMateria);
        valutazioniTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cerchioVerde.setVisibility(View.GONE);
                numeroTextView.setVisibility(View.GONE);
                materiaTextView.setVisibility(View.GONE);
                valutazioniTextView.setVisibility(View.GONE);
                noteTextView.setVisibility(View.GONE);
                assenzeTextView.setVisibility(View.GONE);
                materieLayout.setVisibility(View.VISIBLE);
                votiLayout.setVisibility(View.GONE);
                buttonMaterieIndietro.setVisibility(View.VISIBLE);
                materieLayout.removeAllViews();
                materieLayout.addView(titoloValutazioni);

                for (int i = 0; i < materie.size(); i++) {
                    final int index = i;
                    Button MateriaButton = new Button(studenteActivity.this);
                    MateriaButton.setText(materie.get(i));
                    MateriaButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            buttonMaterieIndietro.setVisibility(View.GONE);
                            materieLayout.setVisibility(View.GONE);
                            nomeMateria.setText(materie.get(index)); //nome della materia
                            votiLayout.removeAllViews();
                            votiLayout.setVisibility(View.VISIBLE);
                            buttonVotiIndietro.setVisibility(View.VISIBLE);
                            votiLayout.addView(votiTextView);
                            for (int j = 0; j < studente.getVoti().size(); j++) {
                                Button voto = new Button(studenteActivity.this);
                                voto.setText(String.valueOf(studente.getVoti().get(j)));
                                votiLayout.addView(voto);
                            }
                        }
                    });
                    materieLayout.addView(MateriaButton);
                }
            }
        });

        TextView noteTextView = findViewById(R.id.note);

        noteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cerchioVerde.setVisibility(View.GONE);
                numeroTextView.setVisibility(View.GONE);
                materiaTextView.setVisibility(View.GONE);
                valutazioniTextView.setVisibility(View.GONE);
                noteTextView.setVisibility(View.GONE);
                assenzeTextView.setVisibility(View.GONE);
                materieLayout.setVisibility(View.GONE);
                votiLayout.setVisibility(View.GONE);
                noteLayout.setVisibility(View.VISIBLE);
                buttonNoteIndietro.setVisibility(View.VISIBLE);

                for (int i = 0; i < studente.getNote().size(); i++) {
                    Button nota = new Button(studenteActivity.this);
                    nota.setText(studente.getNote().get(i).getDate().toString());

                    final String noteInfo = studente.getNote().get(i).getText();
                    nota.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Dialog noteDialog = new Dialog(studenteActivity.this);
                            noteDialog.setContentView(R.layout.dialog_note_info);
                            TextView noteInfoTextView = noteDialog.findViewById(R.id.noteInfoTextView);
                            noteInfoTextView.setText(noteInfo);


                            noteDialog.getWindow().setLayout(
                                    WindowManager.LayoutParams.MATCH_PARENT,
                                    WindowManager.LayoutParams.WRAP_CONTENT);
                            noteDialog.getWindow().setGravity(Gravity.BOTTOM);
                            noteDialog.show();
                        }
                    });

                    noteLayout.addView(nota);
                }
            }
        });


        TextView assenzeText = findViewById(R.id.assenze);

        assenzeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cerchioVerde.setVisibility(View.GONE);
                numeroTextView.setVisibility(View.GONE);
                materiaTextView.setVisibility(View.GONE);
                valutazioniTextView.setVisibility(View.GONE);
                noteTextView.setVisibility(View.GONE);
                assenzeTextView.setVisibility(View.GONE);
                materieLayout.setVisibility(View.GONE);
                votiLayout.setVisibility(View.GONE);
                assenzeLayout.setVisibility(View.VISIBLE);
                buttonAssenzaIndietro.setVisibility(View.VISIBLE);

                for (Assenza assenza : studente.getAssenze()) {
                    String[] parts = new String[0];
                    parts[0]= String.valueOf(assenza.getData());
                     parts[1]= String.valueOf(assenza.getdocente());
                     parts[2]= String.valueOf(assenza.getgiustifica());

                    if (parts.length == 3) {
                        LinearLayout row = new LinearLayout(studenteActivity.this);
                        row.setOrientation(LinearLayout.HORIZONTAL);

                        TextView dataView = new TextView(studenteActivity.this);
                        dataView.setText(parts[0]);
                        dataView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                        dataView.setPadding(16, 16, 16, 16);

                        TextView statoView = new TextView(studenteActivity.this);
                        statoView.setText(parts[1]);
                        statoView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                        statoView.setPadding(16, 16, 16, 16);

                        TextView susView = new TextView(studenteActivity.this);
                        susView.setText(parts[1]);
                        susView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                        susView.setPadding(16, 16, 16, 16);

                        row.addView(dataView);
                        row.addView(statoView);
                        row.addView(susView);
                        
                        assenzeLayout.addView(row);
                    }
                }
            }
        });

        buttonMaterieIndietro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materieLayout.setVisibility(View.GONE);
                buttonMaterieIndietro.setVisibility(View.GONE);
                cerchioVerde.setVisibility(View.VISIBLE);
                numeroTextView.setVisibility(View.VISIBLE);
                materiaTextView.setVisibility(View.VISIBLE);
                noteTextView.setVisibility(View.VISIBLE);
                assenzeTextView.setVisibility(View.VISIBLE);
                valutazioniTextView.setVisibility(View.VISIBLE);

            }
        });

        buttonVotiIndietro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                votiLayout.removeAllViews();
                buttonMaterieIndietro.setVisibility(View.VISIBLE);
                votiLayout.setVisibility(View.GONE);
                buttonVotiIndietro.setVisibility(View.GONE);
                materieLayout.setVisibility(View.VISIBLE);
            }
        });

        buttonNoteIndietro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noteLayout.setVisibility(View.GONE);
                buttonNoteIndietro.setVisibility(View.GONE);
                cerchioVerde.setVisibility(View.VISIBLE);
                numeroTextView.setVisibility(View.VISIBLE);
                materiaTextView.setVisibility(View.VISIBLE);
                noteTextView.setVisibility(View.VISIBLE);
                assenzeTextView.setVisibility(View.VISIBLE);
                valutazioniTextView.setVisibility(View.VISIBLE);
            }
        });

        buttonAssenzaIndietro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assenzeLayout.setVisibility(View.GONE);
                buttonAssenzaIndietro.setVisibility(View.GONE);
                cerchioVerde.setVisibility(View.VISIBLE);
                numeroTextView.setVisibility(View.VISIBLE);
                materiaTextView.setVisibility(View.VISIBLE);
                noteTextView.setVisibility(View.VISIBLE);
                assenzeTextView.setVisibility(View.VISIBLE);
                valutazioniTextView.setVisibility(View.VISIBLE);
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(studenteActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}





