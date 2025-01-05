package com.example.app_registro_elettronico;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * Activity per la schermata del Genitore nell'app.
 * Mostra informazioni sui voti, le assenze e le note dello studente, e permette ai genitori di giustificare le assenze.
 */
public class genitoreActivity extends AppCompatActivity {

    private RelativeLayout cerchioVerde;
    private TextView numeroTextView, materiaTextView, noteTextView, assenzeTextView, noteText, giustificaText, assenzaText, titoloValutazioni, nomeMateria;
    private LinearLayout materieLayout, votiLayout, noteLayout, assenzeLayout, giustificalayout;
    private Button giustifica, logout, buttonMaterieIndietro, bottonegiustifica, buttonVotiIndietro, buttonNoteIndietro, buttonAssenzaIndietro;

    Spinner spinner;
    private ArrayList<String> materie;
    private ArrayList<Integer> voti;

    /**
     * Inizializza l'activity, imposta i componenti UI e gli ascoltatori di eventi.
     *
     * @param savedInstanceState Un bundle che contiene lo stato precedentemente salvato dell'activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genitore);

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
        giustificalayout=findViewById(R.id.giustificaAssenze);
        assenzaText= findViewById(R.id.titoloAssenze);
        giustificaText =findViewById(R.id.titoloGiustifica);
        bottonegiustifica= findViewById(R.id.bottonegiustifica);
        spinner =findViewById(R.id.date);
        noteText = findViewById(R.id.titoloNote);
        voti = new ArrayList<>();
        voti.add(3);
        voti.add(4);
        voti.add(3);
        voti.add(3);

        materie = new ArrayList<>();
        materie.add("Italiano");
        materie.add("Matematica");
        materie.add("Inglese");
        materie.add("Scienze");
        materie.add("Italiano");
        materie.add("Matematica");
        materie.add("Inglese");
        materie.add("Scienze");

        ArrayList<String> note = new ArrayList<>();
        note.add("ciaooo");
        note.add("sus");
        note.add("damn");

        ArrayList<String> assenze= new ArrayList<>();
        assenze.add("2024-12-01 - Giustificata");
        assenze.add("2024-12-03 - Non giustificata");
        assenze.add("2024-12-04 - Non giustificata");
        assenze.add("2024-12-05 - Non giustificata");
        assenze.add("2024-12-06 - Non giustificata");
        assenze.add("2024-12-07 - Non giustificata");
        assenze.add("2024-12-08 - Non giustificata");
        ArrayList<String> assenzeGiustificate = new ArrayList<>();
        ArrayList<String> assenzeNonGiustificate = new ArrayList<>();

        for (String assenza : assenze) {
            if (assenza.contains("Giustificata")) {
                assenzeGiustificate.add(assenza);
            } else if (assenza.contains("Non giustificata")) {
                assenzeNonGiustificate.add(assenza);
            }
        }


        int voto = 5;
        String materia = materie.get(0);

        numeroTextView.setText(String.valueOf(voto));

        if (voto >= 6) {
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
                    Button MateriaButton = new Button(genitoreActivity.this);
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
                            for (int j = 0; j < voti.size(); j++) {
                                Button voto = new Button(genitoreActivity.this);
                                voto.setText(String.valueOf(voti.get(j)));
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
                noteLayout.removeAllViews();
                noteLayout.setVisibility(View.VISIBLE);
                noteLayout.addView(noteText);
                buttonNoteIndietro.setVisibility(View.VISIBLE);

                for (int i = 0; i < note.size(); i++) {
                    Button nota = new Button(genitoreActivity.this);
                    nota.setText(note.get(i));

                    final String noteInfo = note.get(i);
                    nota.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Dialog noteDialog = new Dialog(genitoreActivity.this);
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
                giustificalayout.setVisibility(View.VISIBLE);
                giustificaText.setVisibility(View.VISIBLE);
                assenzeLayout.removeAllViews();
                assenzeLayout.addView(assenzaText);
                for (String assenza : assenzeGiustificate) {
                    String[] parts = assenza.split(" - ");
                    if (parts.length == 2) {
                        LinearLayout row = new LinearLayout(genitoreActivity.this);
                        row.setOrientation(LinearLayout.HORIZONTAL);

                        TextView dataView = new TextView(genitoreActivity.this);
                        dataView.setText(parts[0]);
                        dataView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                        dataView.setPadding(16, 16, 16, 16);

                        TextView statoView = new TextView(genitoreActivity.this);
                        statoView.setText(parts[1]);
                        statoView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                        statoView.setPadding(16, 16, 16, 16);

                        row.addView(dataView);
                        row.addView(statoView);

                        assenzeLayout.addView(row);

                    }
                }

                ArrayList<String> assenzeNonGiustificateDates = new ArrayList<>();
                for (String assenza : assenzeNonGiustificate) {
                    String[] parts = assenza.split(" - ");
                    if (parts.length == 2) {
                        assenzeNonGiustificateDates.add(parts[0]);
                    }
                }

                if (assenzeNonGiustificateDates.isEmpty()) {
                    Toast.makeText(genitoreActivity.this, "Non ci sono assenze non giustificate.", Toast.LENGTH_SHORT).show();
                    return;
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(genitoreActivity.this, android.R.layout.simple_spinner_item, assenzeNonGiustificateDates);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
            }
        });

        bottonegiustifica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (spinner.getSelectedItem() != null) {
                    String selectedDate = spinner.getSelectedItem().toString();

                    String assenzaNonGiustificata = selectedDate + " - Non giustificata";
                    if (assenzeNonGiustificate.contains(assenzaNonGiustificata)) {
                        assenzeNonGiustificate.remove(assenzaNonGiustificata);

                        assenzeGiustificate.add(selectedDate + " - Giustificata");

                        ArrayList<String> assenzeNonGiustificateDates = new ArrayList<>();
                        for (String assenza : assenzeNonGiustificate) {
                            String[] parts = assenza.split(" - ");
                            if (parts.length == 2) {
                                assenzeNonGiustificateDates.add(parts[0]);
                            }
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(genitoreActivity.this, android.R.layout.simple_spinner_item, assenzeNonGiustificateDates);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(adapter);

                        Toast.makeText(genitoreActivity.this, "Assenza giustificata: " + selectedDate, Toast.LENGTH_SHORT).show();

                        assenzeLayout.removeAllViews();
                        assenzeLayout.addView(assenzaText);
                        for (String assenza : assenzeGiustificate) {
                            String[] parts = assenza.split(" - ");
                            if (parts.length == 2) {
                                LinearLayout row = new LinearLayout(genitoreActivity.this);
                                row.setOrientation(LinearLayout.HORIZONTAL);

                                TextView dataView = new TextView(genitoreActivity.this);
                                dataView.setText(parts[0]);
                                dataView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                                dataView.setPadding(16, 16, 16, 16);

                                TextView statoView = new TextView(genitoreActivity.this);
                                statoView.setText(parts[1]);
                                statoView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                                statoView.setPadding(16, 16, 16, 16);

                                row.addView(dataView);
                                row.addView(statoView);

                                assenzeLayout.addView(row);
                            }
                        }
                    }
                } else {
                    Toast.makeText(genitoreActivity.this, "non ci sono assenze da giustificare", Toast.LENGTH_SHORT).show();
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
                giustificalayout.setVisibility(View.GONE);
                assenzeLayout.setVisibility(View.GONE);
                buttonAssenzaIndietro.setVisibility(View.GONE);
                cerchioVerde.setVisibility(View.VISIBLE);
                numeroTextView.setVisibility(View.VISIBLE);
                materiaTextView.setVisibility(View.VISIBLE);
                noteTextView.setVisibility(View.VISIBLE);
                assenzeTextView.setVisibility(View.VISIBLE);
                valutazioniTextView.setVisibility(View.VISIBLE);
                giustificalayout.setVisibility(View.GONE);
            }
        });



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(genitoreActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}





