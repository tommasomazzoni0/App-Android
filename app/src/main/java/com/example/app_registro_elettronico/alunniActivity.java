package com.example.app_registro_elettronico;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.app_registro_elettronico.gestione.Classe;
import com.example.app_registro_elettronico.gestione.Docente;
import com.example.app_registro_elettronico.gestione.Studente;

import java.util.ArrayList;

/**
 * Activity che mostra l'elenco degli alunni per ogni classe
 */
public class alunniActivity extends AppCompatActivity {

    private Classe classe;
    private ArrayList<Classe> classi;
    private ArrayList<Studente> alunni;
    private Docente docente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alunni);

        Intent intent = getIntent();
        docente = (Docente) intent.getSerializableExtra("docente");
        classi = (ArrayList<Classe>) intent.getSerializableExtra("classi");
        classe = (Classe) intent.getSerializableExtra("classe");
        alunni = (ArrayList<Studente>) intent.getSerializableExtra("alunni");


        TextView classeTitle = findViewById(R.id.classeTitle);
        LinearLayout alunniLayout = findViewById(R.id.alunniLayout);
        Button backButton = findViewById(R.id.backButton);

        classeTitle.setText("Alunni della " + classe);

        if (alunni != null) {
            for (Studente alunno : alunni) {
                Button button = new Button(this);
                button.setText(alunno.getNome() + " " + alunno.getCognome());
                button.setBackgroundColor(Color.parseColor("#333333"));
                button.setTextColor(getResources().getColor(android.R.color.white));

                button.setOnClickListener(v -> {
                    Intent intent2 = new Intent(alunniActivity.this, alunniDocenteActivity.class);
                    intent2.putExtra("classi", classi);
                    intent2.putExtra("classe", classe);
                    intent2.putExtra("alunni", alunni);
                    intent2.putExtra("docente",docente);
                    intent2.putExtra("alunno_selezionato", alunno);
                    startActivity(intent2);
                    finish();
                });

                alunniLayout.addView(button);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(0, 8, 0, 8);
                button.setLayoutParams(params);
            }
        } else {
            Log.e("alunniActivity", "La lista degli alunni è vuota");
        }

        backButton.setOnClickListener(v -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("docente", getIntent().getSerializableExtra("docente"));
            resultIntent.putExtra("classi", classi);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}
