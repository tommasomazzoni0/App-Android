package com.example.app_registro_elettronico;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.app_registro_elettronico.gestione.Assenza;
import com.example.app_registro_elettronico.gestione.Studente;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Activity che gestisce l'interfaccia studente, visualizzando i dettagli come voti, note e assenze.
 */
public class studenteActivity extends AppCompatActivity {

    Server server = new Server();
    private RelativeLayout cerchioVerde;
    private TextView numeroTextView, titoloNote, materiaTextView, noteTextView, assenzeTextView, titoloValutazioni, nomeMateria, titoloAssenze;
    private LinearLayout materieLayout, votiLayout, noteLayout, assenzeLayout;
    private Button logout, buttonMaterieIndietro, buttonVotiIndietro, buttonNoteIndietro, buttonAssenzaIndietro;
    private ArrayList<String> materie;
    ArrayList<Studente> studenti;
    Studente studente;
    ArrayList<String> matematica= new ArrayList<>();
    ArrayList<String> italiano= new ArrayList<>();
    ArrayList<String> storia= new ArrayList<>();
    ArrayList<String> informatica= new ArrayList<>();
    ArrayList<String> sistemi= new ArrayList<>();
    private String username, password;

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
        username = (String) intent.getSerializableExtra("username");
        studente = prendiStudenti(username);


        cerchioVerde = findViewById(R.id.cerchio_verde);
        numeroTextView = findViewById(R.id.numero);
        materiaTextView = findViewById(R.id.media);
        noteTextView = findViewById(R.id.note);
        assenzeTextView = findViewById(R.id.assenze);
        titoloAssenze = findViewById(R.id.titoloAssenze);
        materieLayout = findViewById(R.id.materieLayout);
        titoloValutazioni = findViewById(R.id.titoloValutazioni);
        votiLayout = findViewById(R.id.votiLayout);
        noteLayout = findViewById(R.id.noteElenco);
        assenzeLayout = findViewById(R.id.assenzeElenco);
        nomeMateria = findViewById(R.id.titoloMateria);
        titoloNote= findViewById(R.id.titoloNote);
        logout = findViewById(R.id.logOutButton);
        buttonMaterieIndietro = findViewById(R.id.backButtonMaterie);
        buttonAssenzaIndietro = findViewById(R.id.backButtonAssenze);
        buttonVotiIndietro = findViewById(R.id.backButtonVoti);
        buttonNoteIndietro = findViewById(R.id.backButtonNote);
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
                            nomeMateria.setText(materie.get(index));
                            votiLayout.removeAllViews();
                            votiLayout.setVisibility(View.VISIBLE);
                            buttonVotiIndietro.setVisibility(View.VISIBLE);
                            votiLayout.addView(votiTextView);

                            ArrayList<String> votiPerMateria = new ArrayList<>();
                            for (int j = 0; j < studente.getVoti().size(); j++) {
                                if (studente.getVoti().get(j).getMateria().equals(materie.get(index))) {
                                    votiPerMateria.add(String.valueOf(studente.getVoti().get(j).getvoto()));
                                    Button voto = new Button(studenteActivity.this);
                                    voto.setText(String.valueOf(studente.getVoti().get(j).getvoto()));
                                    votiLayout.addView(voto);
                                }
                            }

                            if (votiPerMateria.isEmpty()) {
                                TextView noVotiTextView = new TextView(studenteActivity.this);
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
                noteLayout.addView(titoloNote);
                noteLayout.setVisibility(View.VISIBLE);
                buttonNoteIndietro.setVisibility(View.VISIBLE);

                if (studente.getNote().size() > 0) {
                    for (int i = 0; i < studente.getNote().size(); i++) {
                        Button nota = new Button(studenteActivity.this);

                        String dataString = studente.getNote().get(i).getstringData();

                        nota.setText(dataString);

                        String noteInfo = studente.getNote().get(i).getText();
                        noteInfo = noteInfo.replace("Motivo_", "");
                        String docente = "Docente: " + studente.getNote().get(i).getProfessor().getNome() + " " + studente.getNote().get(i).getProfessor().getCognome();
                        final String info = "Motivazione: " + noteInfo + "\n" + docente;

                        nota.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Dialog noteDialog = new Dialog(studenteActivity.this);
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
                } else {
                    TextView noNoteTextView = new TextView(studenteActivity.this);
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
                // Nascondere altri elementi dell'interfaccia
                cerchioVerde.setVisibility(View.GONE);
                numeroTextView.setVisibility(View.GONE);
                materiaTextView.setVisibility(View.GONE);
                valutazioniTextView.setVisibility(View.GONE);
                noteTextView.setVisibility(View.GONE);
                assenzeTextView.setVisibility(View.GONE);
                materieLayout.setVisibility(View.GONE);
                votiLayout.setVisibility(View.GONE);

                // Pulire e preparare il layout delle assenze
                assenzeLayout.removeAllViews();
                assenzeLayout.addView(titoloAssenze);
                assenzeLayout.setVisibility(View.VISIBLE);
                buttonAssenzaIndietro.setVisibility(View.VISIBLE);

                // Controllo se ci sono assenze
                if (studente.getAssenze().size() > 0) {
                    for (int i = 0; i < studente.getAssenze().size(); i++) {
                        String[] parts = new String[3];
                        String dataString = studente.getAssenze().get(i).getstringData();

                        parts[0] = dataString;
                        parts[1] = studente.getAssenze().get(i).getdocente().getNome() + " " + studente.getAssenze().get(i).getdocente().getCognome();
                        String giustifica = studente.getAssenze().get(i).getgiustifica() ? "Giustificata" : "Non giustificata";
                        parts[2] = giustifica;

                        // Creazione di una riga per ogni assenza
                        LinearLayout row = new LinearLayout(studenteActivity.this);
                        row.setOrientation(LinearLayout.HORIZONTAL);

                        TextView dataView = new TextView(studenteActivity.this);
                        dataView.setText(parts[0]);
                        dataView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                        dataView.setPadding(16, 16, 16, 16);

                        TextView docenteView = new TextView(studenteActivity.this);
                        docenteView.setText(parts[1]);
                        docenteView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 2));
                        docenteView.setPadding(16, 16, 16, 16);

                        TextView giustificaView = new TextView(studenteActivity.this);
                        giustificaView.setText(parts[2]);
                        giustificaView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                        giustificaView.setPadding(16, 16, 16, 16);

                        // Aggiunta delle TextView alla riga
                        row.addView(dataView);
                        row.addView(docenteView);
                        row.addView(giustificaView);

                        // Aggiunta della riga al layout delle assenze
                        assenzeLayout.addView(row);
                    }
                } else {
                    // Messaggio se non ci sono assenze
                    TextView noAssenzeView = new TextView(studenteActivity.this);
                    noAssenzeView.setText("Non ci sono assenze registrate.");
                    noAssenzeView.setTextSize(16);
                    noAssenzeView.setGravity(Gravity.CENTER);
                    noAssenzeView.setPadding(16, 16, 16, 16);
                    assenzeLayout.addView(noAssenzeView);
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


    public Studente prendiStudenti(String username) {
        Log.d("tommaso", "chiamo il bro");

        final Studente[] result = {null};

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Studente> future = executor.submit(() -> {
            Server server = new Server();
            ArrayList<Studente> studenti = server.getStudentiServer();

            if (studenti != null) {
                for (Studente s : studenti) {
                    Log.d("tommaso", s.getCredenziali().getUser());
                    if (s.getCredenziali().getUser().equals(username)) {

                        return s;
                    }
                }
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




