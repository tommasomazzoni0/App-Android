package com.example.app_registro_elettronico;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.app_registro_elettronico.gestione.Assenza;
import com.example.app_registro_elettronico.gestione.Genitore;
import com.example.app_registro_elettronico.gestione.Studente;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Activity per la schermata del Genitore nell'app.
 * Mostra informazioni sui voti, le assenze e le note dello studente, e permette ai genitori di giustificare le assenze.
 */
public class genitoreActivity extends AppCompatActivity {

    private RelativeLayout cerchioVerde;
    private TextView numeroTextView, materiaTextView, noteTextView, assenzeTextView, noteText, giustificaText, assenzaText, titoloValutazioni, nomeMateria;
    private LinearLayout materieLayout, votiLayout, noteLayout, assenzeLayout, giustificalayout;
    private Button giustifica, logout, buttonMaterieIndietro, bottonegiustifica, buttonVotiIndietro, buttonNoteIndietro, buttonAssenzaIndietro;
    Genitore genitore;
    Spinner spinner;
    private ArrayList<String> materie;

    /**
     * Inizializza l'activity, imposta i componenti UI e gli ascoltatori di eventi.
     *
     * @param savedInstanceState Un bundle che contiene lo stato precedentemente salvato dell'activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genitore);
        Intent intent = getIntent();
        genitore = (Genitore) intent.getSerializableExtra("studente");
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

        double media = genitore.getmedia();
        ArrayList<String> materie= new ArrayList<>();
        materie.add("Matematica");
        materie.add("Italiano");
        materie.add("Storia");
        materie.add("Informatica");
        materie.add("Sistemi");


        ArrayList<Assenza> assenze = new ArrayList<>();
        for (Assenza assenza : genitore.getFiglio().getAssenze()) {
            assenze.add(assenza);
        }
        ArrayList<Assenza> assenzeGiustificate = new ArrayList<>();
        ArrayList<Assenza> assenzeNonGiustificate = new ArrayList<>();


        for (Assenza assenza : assenze) {
            if (assenza.getgiustifica()==true) {
                assenzeGiustificate.add(assenza);
            } else if (assenza.getgiustifica()==false) {
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
                            for (int j = 0; j < genitore.getFiglio().getVoti().size(); j++) {
                                Button voto = new Button(genitoreActivity.this);
                                voto.setText(String.valueOf(genitore.getFiglio().getVoti().get(j)));
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

                for (int i = 0; i < genitore.getFiglio().getNote().size(); i++) {
                    Button nota = new Button(genitoreActivity.this);
                    nota.setText(genitore.getFiglio().getNote().get(i).getDate().toString());

                    final String noteInfo = genitore.getFiglio().getNote().get(i).getText();
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

                // Aggiunge le assenze giustificate alla vista
                for (Assenza assenza : assenzeGiustificate) {
                    LinearLayout row = new LinearLayout(genitoreActivity.this);
                    row.setOrientation(LinearLayout.HORIZONTAL);

                    TextView dataView = new TextView(genitoreActivity.this);
                    dataView.setText(String.valueOf(assenza.getData()));
                    dataView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                    dataView.setPadding(16, 16, 16, 16);

                    TextView docenteView = new TextView(genitoreActivity.this);
                    docenteView.setText(assenza.getdocente().getNome()+" "+assenza.getdocente().getCognome());
                    docenteView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                    docenteView.setPadding(16, 16, 16, 16);

                    TextView statoView = new TextView(genitoreActivity.this);
                    statoView.setText("Giustificata");
                    statoView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                    statoView.setPadding(16, 16, 16, 16);

                    row.addView(dataView);
                    row.addView(docenteView);
                    row.addView(statoView);

                    assenzeLayout.addView(row);
                }

                // Configura lo spinner con le assenze non giustificate
                if (assenzeNonGiustificate.isEmpty()) {
                    Toast.makeText(genitoreActivity.this, "Non ci sono assenze non giustificate.", Toast.LENGTH_SHORT).show();
                    return;
                }

                ArrayAdapter<Assenza> adapter = new ArrayAdapter<>(genitoreActivity.this, android.R.layout.simple_spinner_item, assenzeNonGiustificate);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
            }
        });

        bottonegiustifica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Assenza selectedAssenza = (Assenza) spinner.getSelectedItem();
                if (selectedAssenza != null) {
                    // Rimuove l'assenza non giustificata e la aggiunge a quelle giustificate
                    assenzeNonGiustificate.remove(selectedAssenza);
                    selectedAssenza.setgiustifica(true);
                    assenzeGiustificate.add(selectedAssenza);

                    // Aggiorna lo spinner
                    ArrayAdapter<Assenza> adapter = new ArrayAdapter<>(genitoreActivity.this, android.R.layout.simple_spinner_item, assenzeNonGiustificate);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);

                    // Mostra un messaggio di conferma
                    Toast.makeText(genitoreActivity.this, "Assenza giustificata: " + selectedAssenza.getData(), Toast.LENGTH_SHORT).show();

                    // Aggiorna la vista delle assenze giustificate
                    assenzeLayout.removeAllViews();
                    assenzeLayout.addView(assenzaText);
                    for (Assenza assenza : assenzeGiustificate) {
                        LinearLayout row = new LinearLayout(genitoreActivity.this);
                        row.setOrientation(LinearLayout.HORIZONTAL);

                        TextView dataView = new TextView(genitoreActivity.this);
                        dataView.setText(String.valueOf(assenza.getData()));
                        dataView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                        dataView.setPadding(16, 16, 16, 16);

                        TextView docenteView = new TextView(genitoreActivity.this);
                        docenteView.setText(assenza.getdocente().getNome() + " " + assenza.getdocente().getCognome());
                        docenteView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                        docenteView.setPadding(16, 16, 16, 16);

                        TextView statoView = new TextView(genitoreActivity.this);
                        statoView.setText("Giustificata");
                        statoView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                        statoView.setPadding(16, 16, 16, 16);

                        row.addView(dataView);
                        row.addView(docenteView);
                        row.addView(statoView);

                        assenzeLayout.addView(row);

                        ArrayList<Assenza> elencoAssenze = ordinaAssenze(assenzeGiustificate,assenzeNonGiustificate);
                        genitore.getFiglio().setAssenze(elencoAssenze);
                    }
                } else {
                    Toast.makeText(genitoreActivity.this, "Non ci sono assenze da giustificare.", Toast.LENGTH_SHORT).show();
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

    public static ArrayList<Assenza> ordinaAssenze(ArrayList<Assenza> assenzeGiustificate, ArrayList<Assenza> assenzeNonGiustificate) {
        ArrayList<Assenza> assenze = new ArrayList<>();

        // Aggiungi le due liste alla nuova lista
        assenze.addAll(assenzeGiustificate);
        assenze.addAll(assenzeNonGiustificate);

        // Ordina la lista unificata per data
        assenze.sort((a1, a2) -> a1.getData().compareTo(a2.getData()));

        // Restituisci la lista ordinata
        return assenze;
    }

}







