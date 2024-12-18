package com.example.app_registro_elettronico;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class alunniDocenteActivity extends AppCompatActivity {

    TextView valutazioniTextView, noteTextView, assenzeTextView;
    Button logout, indietro;
    String classe;
    ArrayList<String> alunni;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docente);
        classe = getIntent().getStringExtra("classe_nome");
        alunni = getIntent().getStringArrayListExtra("alunni");

        valutazioniTextView = findViewById(R.id.valutazioni);
        noteTextView = findViewById(R.id.note);
        assenzeTextView = findViewById(R.id.assenze);

        valutazioniTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(alunniDocenteActivity.this, inserimentoValutazioniActivity.class);
                intent.putExtra("classe_nome", classe);
                intent.putStringArrayListExtra("alunni", alunni);
                startActivity(intent);
                finish();
            }
        });

        noteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(alunniDocenteActivity.this, inserimentoNoteActivity.class);
                intent.putExtra("classe_nome", classe);
                intent.putStringArrayListExtra("alunni", alunni);
                startActivity(intent);
                finish();
            }
        });

        assenzeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(alunniDocenteActivity.this, inserimentoAssenzeActivity.class);
                intent.putExtra("classe_nome", classe);
                intent.putStringArrayListExtra("alunni", alunni);
                startActivity(intent);
                finish();
            }
        });

        indietro = findViewById(R.id.indietro);
        indietro.setOnClickListener(v -> {
            Intent intent = new Intent(alunniDocenteActivity.this, alunniActivity.class);
            intent.putExtra("classe_nome", classe);
            intent.putStringArrayListExtra("alunni", alunni);
            startActivity(intent);
            finish();
        });


        logout = findViewById(R.id.logOutButton);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(alunniDocenteActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }


}
