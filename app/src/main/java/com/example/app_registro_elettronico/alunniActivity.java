package com.example.app_registro_elettronico;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

/**
 * La classe alunniActivity rappresenta un'attività dell'app che mostra la lista degli alunni di una classe.
 * L'utente può visualizzare i dettagli di ogni alunno o tornare all'attività precedente.
 */
public class alunniActivity extends AppCompatActivity {

    Button logout;

    /**
     * Metodo chiamato durante la creazione dell'attività.
     * Inizializza la vista, configura i pulsanti e popola la lista degli alunni.
     *
     * @param savedInstanceState Lo stato salvato dell'attività, se presente.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alunni);

        TextView classeTitle = findViewById(R.id.classeTitle);
        LinearLayout alunniLayout = findViewById(R.id.alunniLayout);
        Button backButton = findViewById(R.id.backButton);

        String classe = getIntent().getStringExtra("classe_nome");
        ArrayList<String> alunni = getIntent().getStringArrayListExtra("alunni");

        classeTitle.setText("Alunni della " + classe);

        if (alunni != null) {
            for (String alunno : alunni) {
                Button button = new Button(this);
                button.setText(alunno);
                button.setBackgroundColor(Color.parseColor("#333333"));
                button.setTextColor(getResources().getColor(android.R.color.white));

                button.setOnClickListener(v -> {
                    Intent intent = new Intent(alunniActivity.this, alunniDocenteActivity.class);
                    intent.putExtra("classe_nome", classe);
                    intent.putStringArrayListExtra("alunni", alunni);
                    startActivity(intent);
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
            Intent intent = new Intent(alunniActivity.this, docenteActivity.class);
            startActivity(intent);
            finish();
        });
    }


    /**
     * Metodo chiamato quando un'attività figlia restituisce un risultato.
     * Aggiorna la lista degli alunni se ci sono modifiche.
     * @param requestCode Il codice della richiesta originale.
     * @param resultCode  Il risultato restituito dall'attività figlia.
     * @param data        I dati aggiuntivi restituiti dall'attività figlia.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            String classe = data.getStringExtra("classe_nome");
            ArrayList<String> alunni = data.getStringArrayListExtra("alunni");

            if (classe != null && alunni != null) {
                TextView classeTitle = findViewById(R.id.classeTitle);
                classeTitle.setText("Alunni della " + classe);

                LinearLayout alunniLayout = findViewById(R.id.alunniLayout);
                alunniLayout.removeAllViews();  // Pulisci la lista precedente

                for (String alunno : alunni) {
                    Button button = new Button(this);
                    button.setText(alunno);
                    button.setBackgroundColor(Color.parseColor("#333333"));
                    button.setTextColor(getResources().getColor(android.R.color.white));

                    button.setOnClickListener(v -> {
                        Intent intent = new Intent(alunniActivity.this, alunniDocenteActivity.class);
                        intent.putExtra("classe_nome", classe);
                        intent.putStringArrayListExtra("alunni", alunni);
                        startActivityForResult(intent, 1);
                    });


                    alunniLayout.addView(button);

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    params.setMargins(0, 8, 0, 8);
                    button.setLayoutParams(params);
                }
            }
        }
    }

}
