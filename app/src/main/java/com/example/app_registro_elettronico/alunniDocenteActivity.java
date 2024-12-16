package com.example.app_registro_elettronico;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class alunniDocenteActivity extends AppCompatActivity {

    TextView valutazioniTextView, noteTextView, assenzeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docente);


        valutazioniTextView = findViewById(R.id.valutazioni);
        noteTextView = findViewById(R.id.note);
        assenzeTextView = findViewById(R.id.assenze);

        valutazioniTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(alunniDocenteActivity.this, inserimentoValutazioniActivity.class);
                startActivity(intent);
                finish();
            }
        });

        noteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(alunniDocenteActivity.this, inserimentoNoteActivity.class);
                startActivity(intent);
                finish();
            }
        });

        assenzeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(alunniDocenteActivity.this, inserimentoAssenzeActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }
}
