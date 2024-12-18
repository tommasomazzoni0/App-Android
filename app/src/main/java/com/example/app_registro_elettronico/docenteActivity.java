package com.example.app_registro_elettronico;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class docenteActivity extends AppCompatActivity {
    private Map<String, ArrayList<String>> classiAlunniMap = new HashMap<>();
    TextView alunni;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.docente_main);
        populateClassiAndAlunni();

        LinearLayout classListLayout = findViewById(R.id.classListLayout);
        alunni = findViewById(R.id.leTueClassi);

        for (String classe : classiAlunniMap.keySet()) {
            Button button = new Button(this);
            button.setText(classe);
            button.setBackgroundColor(Color.parseColor("#333333"));
            button.setTextColor(getResources().getColor(android.R.color.white));

            button.setOnClickListener(v -> {
                ArrayList<String> alunni = classiAlunniMap.get(classe);
                Intent intent = new Intent(docenteActivity.this, alunniActivity.class);
                intent.putExtra("classe_nome", classe);
                intent.putStringArrayListExtra("alunni", alunni);
                startActivity(intent);
            });

            classListLayout.addView(button);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 8, 0, 8);
            button.setLayoutParams(params);
        }

        logout = findViewById(R.id.logOutButton);

        logout.setOnClickListener(view -> {
            Intent intent = new Intent(docenteActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
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
}
