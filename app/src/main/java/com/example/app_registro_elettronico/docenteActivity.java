package com.example.app_registro_elettronico;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class docenteActivity extends AppCompatActivity {
    private Map<String, ArrayList<String>> classiAlunniMap = new HashMap<>();
    TextView alunni,classi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.docente_main);
        populateClassiAndAlunni();
        LinearLayout classListLayout = findViewById(R.id.classListLayout);
        alunni = findViewById(R.id.leTueClassi);
        classi = findViewById(R.id.Alunni);

        for (String classe : classiAlunniMap.keySet()) {
            alunni.setVisibility(View.VISIBLE);
            Button button = new Button(this);
            button.setText(classe);
            button.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
            button.setTextColor(getResources().getColor(android.R.color.white));

            button.setOnClickListener(v -> {
                showAlunniOfClasse(classe);
                alunni.setVisibility(View.GONE);
            });

            classListLayout.addView(button);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 8, 0, 8);
            button.setLayoutParams(params);
        }
    }

    private void populateClassiAndAlunni() {
        ArrayList<String> alunniClasse1 = new ArrayList<>();
        alunniClasse1.add("Alunno 1");
        alunniClasse1.add("Alunno 2");
        alunniClasse1.add("Alunno 3");

        ArrayList<String> alunniClasse2 = new ArrayList<>();
        alunniClasse2.add("Alunno 4");
        alunniClasse2.add("Alunno 5");
        alunniClasse2.add("Alunno 6");

        classiAlunniMap.put("Classe 1", alunniClasse1);
        classiAlunniMap.put("Classe 2", alunniClasse2);
    }

    private void showAlunniOfClasse(String classe) {
        LinearLayout classListLayout = findViewById(R.id.classListLayout);
        classListLayout.removeAllViews();

        List<String> alunni = classiAlunniMap.get(classe);

            for (String alunno : alunni) {
                Button button = new Button(this);
                button.setText(alunno);
                button.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                button.setTextColor(getResources().getColor(android.R.color.white));

                button.setOnClickListener(v -> {
                    Intent intent = new Intent(docenteActivity.this, alunniDocenteActivity.class);
                    intent.putExtra("alunno_nome", alunno);
                    startActivity(intent);
                });

                classListLayout.addView(button);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(0, 8, 0, 8);
                button.setLayoutParams(params);
            }
    }
}
