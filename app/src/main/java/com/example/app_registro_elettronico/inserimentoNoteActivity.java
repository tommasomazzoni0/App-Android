package com.example.app_registro_elettronico;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.app_registro_elettronico.gestione.*;

import java.util.ArrayList;
import java.util.List;

public class inserimentoNoteActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private notesAdapter notesAdapter;
    private List<Note> noteList;
    Button indietro,inserisci;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_activity);
        indietro = findViewById(R.id.indietro);
        indietro.setVisibility(View.VISIBLE);
        inserisci = findViewById(R.id.inserisci);
        inserisci.setVisibility(View.VISIBLE);
        recyclerView = findViewById(R.id.notesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        noteList = new ArrayList<>();
        noteList.add(new Note("01/01/2024", "Mario Rossi", "Nota di esempio 1"));
        noteList.add(new Note("02/01/2024", "Giovanni Bianchi", "Nota di esempio 2"));
        noteList.add(new Note("02/01/2024", "Giovanni Bianchi", "Nota di esempio 2"));
        noteList.add(new Note("02/01/2024", "Giovanni Bianchi", "Nota di esempio 2"));
        notesAdapter = new notesAdapter(noteList);
        recyclerView.setAdapter(notesAdapter);


        indietro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(inserimentoNoteActivity.this, alunniDocenteActivity.class);
                startActivity(intent);
                finish();
            }
        });

        inserisci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
