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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Activity per la schermata del Genitore nell'app.
 * Mostra informazioni sui voti, le assenze e le note dello studente, e permette ai genitori di giustificare le assenze.
 */
public class genitoreActivity extends AppCompatActivity {
    Server server= new Server();
    private RelativeLayout cerchioVerde;
    private TextView numeroTextView, materiaTextView, noteTextView, assenzeTextView, noteText, giustificaText, assenzaText, titoloValutazioni, nomeMateria;
    private LinearLayout materieLayout, votiLayout, noteLayout, assenzeLayout, giustificalayout;
    private Button giustifica, logout, buttonMaterieIndietro, bottonegiustifica, buttonVotiIndietro, buttonNoteIndietro, buttonAssenzaIndietro;
    Studente studente;
    Spinner spinner;
    String username;
    ArrayList<String> matematica= new ArrayList<>();
    ArrayList<String> italiano= new ArrayList<>();
    ArrayList<String> storia= new ArrayList<>();
    ArrayList<String> informatica= new ArrayList<>();
    ArrayList<String> sistemi= new ArrayList<>();
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
        username = (String) intent.getSerializableExtra("username");
        studente = prendiStudente(username);
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

        double media=0;
        if (studente != null) {
            media = studente.getmedia();
        } else {
            Log.e("StudenteActivity", "Studente è null, impossibile ottenere la media.");
        }

        ArrayList<String> materie = new ArrayList<>();
        studente.getVoti().get(0).getMateria();
        materie.add("Matematica");
        materie.add("Italiano");
        materie.add("Storia");
        materie.add("Informatica");
        materie.add("Sistemi");

        for (int i = 0; i < studente.getVoti().size(); i++) {
            if(studente.getVoti().get(i).getMateria().equals("Matematica")){
                matematica.add(String.valueOf(studente.getVoti().get(i).getvoto()));
            }else if(studente.getVoti().get(i).getMateria().equals("Italiano")){
                italiano.add(String.valueOf(studente.getVoti().get(i).getvoto()));
            }else if(studente.getVoti().get(i).getMateria().equals("Storia")){
                storia.add(String.valueOf(studente.getVoti().get(i).getvoto()));
            }else if(studente.getVoti().get(i).getMateria().equals("Informatica")){
                informatica.add(String.valueOf(studente.getVoti().get(i).getvoto()));
            }else{
                sistemi.add(String.valueOf(studente.getVoti().get(i).getvoto()));
            }
        }


        ArrayList<Assenza> assenze = new ArrayList<>();
        for (Assenza assenza : studente.getAssenze()) {
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
                    Button MateriaButton = new Button(genitoreActivity.this);
                    MateriaButton.setText(materie.get(i));
                    MateriaButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            buttonMaterieIndietro.setVisibility(View.GONE);
                            materieLayout.setVisibility(View.GONE);
                            nomeMateria.setText(materie.get(index));
                            votiLayout.removeAllViews();
                            votiLayout.setVisibility(View.VISIBLE);
                            buttonVotiIndietro.setVisibility(View.VISIBLE);
                            votiLayout.addView(votiTextView);

                            ArrayList<String> votiPerMateria = new ArrayList<>();
                            for (int j = 0; j < studente.getVoti().size(); j++) {
                                if (studente.getVoti().get(j).getMateria().equals(materie.get(index))) {
                                    votiPerMateria.add(String.valueOf(studente.getVoti().get(j).getvoto()));
                                    Button voto = new Button(genitoreActivity.this);
                                    voto.setText(String.valueOf(studente.getVoti().get(j).getvoto()));
                                    votiLayout.addView(voto);
                                }
                            }

                            if (votiPerMateria.isEmpty()) {
                                TextView noVotiTextView = new TextView(genitoreActivity.this);
                                noVotiTextView.setText("Non ci sono voti per questa materia");
                                noVotiTextView.setTextSize(16);
                                noVotiTextView.setGravity(Gravity.CENTER);
                                votiLayout.addView(noVotiTextView);
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

                if (studente.getNote().size() > 0) {
                    for (int i = 0; i < studente.getNote().size(); i++) {
                        Button nota = new Button(genitoreActivity.this);
                        nota.setText(studente.getNote().get(i).getstringData());

                        String noteInfo = studente.getNote().get(i).getText();
                        noteInfo = noteInfo.replace("Motivo_", "");
                        String docente = "Docente: " + studente.getNote().get(i).getProfessor().getNome() + " " + studente.getNote().get(i).getProfessor().getCognome();
                        final String info = "Motivazione: " + noteInfo + "\n" + docente;
                        nota.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Dialog noteDialog = new Dialog(genitoreActivity.this);
                                noteDialog.setContentView(R.layout.dialog_note_info);
                                TextView noteInfoTextView = noteDialog.findViewById(R.id.noteInfoTextView);
                                noteInfoTextView.setText(info);


                                noteDialog.getWindow().setLayout(
                                        WindowManager.LayoutParams.MATCH_PARENT,
                                        WindowManager.LayoutParams.WRAP_CONTENT);
                                noteDialog.getWindow().setGravity(Gravity.BOTTOM);
                                noteDialog.show();
                            }
                        });

                        noteLayout.addView(nota);
                    }
                }else{
                    TextView noNoteTextView = new TextView(genitoreActivity.this);
                    noNoteTextView.setText("Non sono presenti note");
                    noNoteTextView.setTextSize(16);
                    noNoteTextView.setGravity(Gravity.CENTER);
                    noteLayout.addView(noNoteTextView);
                }
            }
        });


        TextView assenzeText = findViewById(R.id.assenze);


        assenzeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView noAssenzeView;
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

                if (studente.getAssenze().size() > 0) {
                    if(!assenzeGiustificate.isEmpty()) {
                        for (Assenza assenza : assenzeGiustificate) {
                            LinearLayout row = new LinearLayout(genitoreActivity.this);
                            row.setOrientation(LinearLayout.HORIZONTAL);

                            TextView dataView = new TextView(genitoreActivity.this);
                            dataView.setText(assenza.getstringData());
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
                        }
                    }else{
                        noAssenzeView = new TextView(genitoreActivity.this);
                        noAssenzeView.setText("Non ci sono assenze giustificate.");
                        noAssenzeView.setTextSize(16);
                        noAssenzeView.setGravity(Gravity.CENTER);
                        noAssenzeView.setPadding(16, 16, 16, 16);
                        assenzeLayout.addView(noAssenzeView);
                    }
                }else{
                    noAssenzeView = new TextView(genitoreActivity.this);
                    noAssenzeView.setText("Non ci sono assenze");
                    noAssenzeView.setTextSize(16);
                    noAssenzeView.setGravity(Gravity.CENTER);
                    noAssenzeView.setPadding(16, 16, 16, 16);
                    assenzeLayout.addView(noAssenzeView);
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
                    
                    // Aggiorna la vista delle assenze giustificate
                    assenzeLayout.removeAllViews();
                    assenzeLayout.addView(assenzaText);
                    for (Assenza assenza : assenzeGiustificate) {
                        LinearLayout row = new LinearLayout(genitoreActivity.this);
                        row.setOrientation(LinearLayout.HORIZONTAL);

                        TextView dataView = new TextView(genitoreActivity.this);
                        dataView.setText(assenza.getstringData());
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
                        studente.setAssenze(elencoAssenze);
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
        assenze.sort((a1, a2) -> a1.getstringData().compareTo(a2.getstringData()));

        // Restituisci la lista ordinata
        return assenze;
    }

    public Studente prendiStudente(String username) {

        final Studente[] result = {null};

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Studente> future = executor.submit(() -> {
            Server server = new Server();
            Studente studente = server.getGenitoriServer(username);

            if (studente != null) {
                return studente;
            }
            return null;
        });

        try {
            result[0] = future.get();
        } catch (Exception e) {
            Log.e("StudenteActivity", "Errore durante la ricerca dello studente", e);
        }

        return result[0];
    }

}







