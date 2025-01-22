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
import com.example.app_registro_elettronico.gestione.Genitore;
import com.example.app_registro_elettronico.gestione.Studente;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Activity principale per la visualizzazione delle classi e degli alunni associati a un docente.
 * Consente al docente di selezionare una classe per visualizzare gli alunni e di effettuare il logout.
 */
public class docenteActivity extends AppCompatActivity {
    private Map<String, ArrayList<String>> classiAlunniMap = new HashMap<>();
    TextView alunni;
    Button logout;
    Docente docente;
    String username;
    ArrayList<Classe> classi= new ArrayList<>();
    /**
     * Metodo chiamato quando l'Activity viene creata.
     * Popola la lista delle classi e degli alunni, e imposta i layout e i listener degli eventi.
     *
     * @param savedInstanceState Stato precedente dell'Activity, se presente.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.docente_main);
        Intent intent = getIntent();
        username = (String) intent.getSerializableExtra("username");
        docente = prendiDocenti();

        LinearLayout classListLayout = findViewById(R.id.classListLayout);
        alunni = findViewById(R.id.leTueClassi);

        for (Classe classe : docente.getClassi()) {
            Button button = new Button(this);
            button.setText(classe.getAnno() + " " + classe.getSezione() + " "+ classe.getIndirizzo());
            button.setBackgroundColor(Color.parseColor("#333333"));
            button.setTextColor(getResources().getColor(android.R.color.white));

            button.setOnClickListener(v -> {
                ArrayList<Studente> alunni = prendiStudenti(classe);
                Intent intent1 = new Intent(docenteActivity.this, alunniActivity.class);
                intent1.putExtra("classi", docente.getClassi());
                intent1.putExtra("classe", classe);
                intent1.putExtra("alunni", alunni);
                startActivity(intent1);
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

    public Docente prendiDocenti(){
        final Docente[] result = {null};

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Docente> future = executor.submit(() -> {
            Server server = new Server();
            Docente doc = server.getDocenti(username);

            if (doc != null) {
                return doc;
            }
            return null;
        });

        try {
            result[0] = future.get();
        } catch (Exception e) {
            Log.e("StudenteActivity", "Errore durante la ricerca dello studente", e);
        }

        return result[0];
    }

    public ArrayList<Studente> prendiStudenti(Classe classe) {
        final ArrayList<Studente> result = new ArrayList<>();

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<ArrayList<Studente>> future = executor.submit(() -> {
            Server server = new Server();
            ArrayList<Studente> studenti = server.getStudentiClassi(classe);
            Log.d("alunni", String.valueOf(studenti.size()));
            if(studenti!=null){
                return studenti;
            }
            return null;
        });

        try {
            result.addAll(future.get());
        } catch (Exception e) {
            Log.e("StudenteActivity", "Errore durante la ricerca degli studenti", e);
        } finally {
            executor.shutdown();
        }

        return result;
    }

}
