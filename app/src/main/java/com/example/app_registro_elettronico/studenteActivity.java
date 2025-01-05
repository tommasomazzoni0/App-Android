package com.example.app_registro_elettronico;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Activity che gestisce l'interfaccia studente, visualizzando i dettagli come voti, note e assenze.
 */
public class studenteActivity extends AppCompatActivity {

    private RelativeLayout cerchioVerde;

    String SERVER_URL= "https://tommasomazzoni.altervista.org/tornaStudente.php";
    private TextView numeroTextView, materiaTextView, noteTextView, assenzeTextView, titoloValutazioni, nomeMateria;
    private LinearLayout materieLayout, votiLayout, noteLayout, assenzeLayout;
    private Button logout, buttonMaterieIndietro, buttonVotiIndietro, buttonNoteIndietro, buttonAssenzaIndietro;

    private ArrayList<String> materie;
    private ArrayList<Integer> voti;
    String username;

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
        username = intent.getStringExtra("username");

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


        sendRequest(username);


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
        assenze.add("2024-12-02 - Non giustificata");

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
                            for (int j = 0; j < voti.size(); j++) {
                                Button voto = new Button(studenteActivity.this);
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
                noteLayout.setVisibility(View.VISIBLE);
                buttonNoteIndietro.setVisibility(View.VISIBLE);

                for (int i = 0; i < note.size(); i++) {
                    Button nota = new Button(studenteActivity.this);
                    nota.setText(note.get(i));

                    final String noteInfo = note.get(i);
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

                for (String assenza : assenze) {
                    String[] parts = assenza.split(" - ");
                    if (parts.length == 2) {
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

                        row.addView(dataView);
                        row.addView(statoView);

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


    /**
     * Invia una richiesta al server per recuperare i dati dello studente.
     *
     * @param username Il nome utente dello studente.
     */
    private void sendRequest(String username) {
        OkHttpClient client = new OkHttpClient();

        FormBody formBody = new FormBody.Builder()
                .add("username", username)
                .build();

        Request request = new Request.Builder()
                .url(SERVER_URL)
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                runOnUiThread(() -> {
                    Toast.makeText(studenteActivity.this, "Errore di connessione", Toast.LENGTH_SHORT).show();
                });
                Log.e("HTTP_ERROR", e.getMessage(), e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String responseBody = response.body().string();
                    runOnUiThread(() -> {
                        try {
                            JSONObject jsonResponse = new JSONObject(responseBody);
                            String status = jsonResponse.getString("status");

                        } catch (JSONException e) {
                            Log.e("JSON_ERROR", e.getMessage(), e);
                        }
                    });
                } else {
                    runOnUiThread(() -> {
                        Toast.makeText(studenteActivity.this, "Errore dal server", Toast.LENGTH_SHORT).show();
                    });
                }
            }
        });
    }
}





