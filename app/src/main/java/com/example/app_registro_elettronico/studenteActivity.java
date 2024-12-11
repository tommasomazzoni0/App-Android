package com.example.app_registro_elettronico;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class studenteActivity extends AppCompatActivity {

    private RelativeLayout cerchioVerde;
    private TextView numeroTextView, materiaTextView, noteTextView, assenzeTextView, titoloValutazioni, nomeMateria;
    private LinearLayout materieLayout, votiLayout;
    private Button buttonMaterieIndietro, buttonVotiIndietro;

    private ArrayList<String> materie;
    private ArrayList<Integer> voti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studente);

        cerchioVerde = findViewById(R.id.cerchio_verde);
        numeroTextView = findViewById(R.id.numero);
        materiaTextView = findViewById(R.id.media);
        noteTextView = findViewById(R.id.note);
        assenzeTextView = findViewById(R.id.assenze);
        materieLayout = findViewById(R.id.materieLayout);
        titoloValutazioni = findViewById(R.id.titoloValutazioni);
        votiLayout = findViewById(R.id.votiLayout);
        nomeMateria = findViewById(R.id.titoloMateria);
        buttonMaterieIndietro = findViewById(R.id.backButtonMaterie);
        buttonVotiIndietro = findViewById(R.id.backButtonVoti);

        voti = new ArrayList<>();
        voti.add(3);
        voti.add(4);
        voti.add(10);
        voti.add(7);

        materie = new ArrayList<>();
        materie.add("Italiano");
        materie.add("Matematica");
        materie.add("Inglese");
        materie.add("Scienze");
        materie.add("Italiano");
        materie.add("Matematica");
        materie.add("Inglese");
        materie.add("Scienze");

        int voto = 6;
        String materia = materie.get(0);

        numeroTextView.setText(String.valueOf(voto));

        if (voto >= 6) {
            cerchioVerde.setBackgroundResource(R.drawable.cerchio_verde);
        } else {
            cerchioVerde.setBackgroundResource(R.drawable.cerchio_rosso);
        }

        TextView valutazioniTextView = findViewById(R.id.valutazioni);
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


                for (int i = 0; i < materie.size(); i++) {
                    final int index = i;
                    Button MateriaButton = new Button(studenteActivity.this);
                    MateriaButton.setText(materie.get(i));
                    MateriaButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            materieLayout.setVisibility(View.GONE);
                            nomeMateria.setText(materie.get(index));
                            votiLayout.setVisibility(View.VISIBLE);
                            buttonVotiIndietro.setVisibility(View.VISIBLE);
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
                votiLayout.setVisibility(View.GONE);
                buttonVotiIndietro.setVisibility(View.GONE);
                materieLayout.setVisibility(View.VISIBLE);
                buttonVotiIndietro.setVisibility(View.VISIBLE);
            }
        });
    }
}



