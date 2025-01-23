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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Activity per la schermata del docente nell'app.
 * si ha la possibilità di vedere e mettere voti, note e assenze
 */
public class docenteActivity extends AppCompatActivity {

    private Docente docente;
    private ArrayList<Classe> classi= new ArrayList<>();
    private TextView alunni;
    private Button logout;
    private String username;

    /**
     * configura l'activity
     * @param savedInstanceState istanza
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.docente_main);

        Intent intent = getIntent();
        username = (String) intent.getSerializableExtra("username");
        docente = (Docente) intent.getSerializableExtra("docente");
        classi = (ArrayList<Classe>) intent.getSerializableExtra("classi");

        if (docente == null || classi == null) {
            docente = prendiDocenti();
            classi = docente.getClassi();
        }

        configuraLayout();
    }

    /**
     * Serve per configurare l'elenco di classi a cui insegna il professore
     * è presente un bottone che, se schiacciato, fa partire una nuova activity
     */
    private void configuraLayout() {
        LinearLayout classListLayout = findViewById(R.id.classListLayout);
        classListLayout.removeAllViews();
        alunni = findViewById(R.id.leTueClassi);

        for (Classe classe : classi) {
            Button button = new Button(this);
            button.setText(classe.getAnno() + " " + classe.getSezione() + " " + classe.getIndirizzo());
            button.setBackgroundColor(Color.parseColor("#333333"));
            button.setTextColor(getResources().getColor(android.R.color.white));

            button.setOnClickListener(v -> {
                ArrayList<Studente> alunni = prendiStudenti(classe);
                Intent intent1 = new Intent(docenteActivity.this, alunniActivity.class);
                intent1.putExtra("classi", classi);
                intent1.putExtra("classe", classe);
                intent1.putExtra("alunni", alunni);
                intent1.putExtra("docente", docente);
                startActivityForResult(intent1, 1);
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
            Intent intent2 = new Intent(docenteActivity.this, MainActivity.class);
            startActivity(intent2);
            finish();
        });
    }

    private Docente prendiDocenti() {
        final Docente[] result = {null};

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Docente> future = executor.submit(() -> {
            Server server = new Server();
            return server.getDocenti(username);
        });

        try {
            result[0] = future.get();
        } catch (Exception e) {
            Log.e("docenteActivity", "Errore durante la ricerca del docente", e);
        } finally {
            executor.shutdown();
        }

        return result[0];
    }


    /**
     * ritorna gli alunni di una determinata classe
     * @param classe la classe che si vuole prendere
     * @return l'arraylist di studenti presenti in quella classe
     */
    private ArrayList<Studente> prendiStudenti(Classe classe) {
        final ArrayList<Studente> result = new ArrayList<>();

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<ArrayList<Studente>> future = executor.submit(() -> {
            Server server = new Server();
            return server.getStudentiClassi(classe);
        });

        try {
            result.addAll(future.get());
        } catch (Exception e) {
            Log.e("docenteActivity", "Errore durante la ricerca degli studenti", e);
        } finally {
            executor.shutdown();
        }

        return result;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            docente = (Docente) data.getSerializableExtra("docente");
            classi = (ArrayList<Classe>) data.getSerializableExtra("classi");
            configuraLayout();
        }
    }
}