package com.example.app_registro_elettronico;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.app_registro_elettronico.gestione.*;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Activity principale per la gestione del login dell'utente.
 * Consente all'utente di accedere inserendo username e password e di navigare verso l'activity corretta
 * in base al ruolo dell'utente (studente, genitore, docente).
 */
public class MainActivity extends AppCompatActivity {
    private static final String USER_URL = "https://tommasomazzoni.altervista.org/WS.php";
    private static final String STUDENTI_URL = "https://tommasomazzoni.altervista.org/studente.php";
    private static final String DOCENTI_URL = "https://tommasomazzoni.altervista.org/docente.php";
    private static final String GENITORI_URL = "https://tommasomazzoni.altervista.org/genitore.php";
    private static final String CLASSI_URL = "https://tommasomazzoni.altervista.org/classe.php";
    private EditText usernameText;
    private EditText passwordText;
    private Button accediButton;
    private TextView errore;
    private String username;

    private ArrayList<Studente> studenti;
    private ArrayList<Docente> docenti;
    private ArrayList<Genitore> genitori;
    private ArrayList<Classe> classi;

    public MainActivity (){
        classi = getClassiServer();
        docenti = getDocentiServer();
        studenti = getStudentiServer();
        genitori = getGenitoriServer();
    }

    /**
     * Metodo di creazione dell'activity.
     * Inizializza le viste, imposta gli ascoltatori di eventi e gestisce la logica di login.
     *
     * @param savedInstanceState Stato precedentemente salvato dell'activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameText = findViewById(R.id.Username);
        passwordText = findViewById(R.id.Password);
        accediButton = findViewById(R.id.accedi);
        errore = findViewById(R.id.errorMessage);

        accediButton.setOnClickListener(view -> {
            username = usernameText.getText().toString().trim();
            String password = passwordText.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                errore.setVisibility(View.VISIBLE);
                errore.setText("Username o password mancanti!");
            } else {
                errore.setVisibility(View.GONE);
                sendRequest("login", username, password);
            }
        });
    }

    /**
     * Metodo per inviare richieste HTTP al server.
     * @param action   Azione da eseguire: "login" o altre azioni.
     * @param username Username dell'utente.
     * @param password Password dell'utente.
     */
    private void sendRequest(String action, String username, String password) {
        OkHttpClient client = new OkHttpClient();

        FormBody formBody = new FormBody.Builder()
                .add("azione", action)
                .add("username", username)
                .add("password", password)
                .build();

        Request request = new Request.Builder()
                .url(USER_URL)
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            /**
             * Metodo invocato in caso di errore nella connessione.
             *
             * @param call La chiamata HTTP.
             * @param e    L'eccezione che è stata generata.
             */
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                runOnUiThread(() -> {
                    errore.setVisibility(View.VISIBLE);
                    errore.setText("Errore di connessione: " + e.getMessage());
                    Toast.makeText(MainActivity.this, "Errore di connessione", Toast.LENGTH_SHORT).show();
                });
                Log.e("HTTP_ERROR", e.getMessage(), e);
            }

            /**
             * Metodo invocato quando il server risponde con successo.
             *
             * @param call     La chiamata HTTP.
             * @param response La risposta ricevuta dal server.
             * @throws IOException In caso di errore nella lettura della risposta.
             */
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String responseBody = response.body().string();
                    runOnUiThread(() -> {
                        try {
                            JSONObject jsonResponse = new JSONObject(responseBody);
                            String status = jsonResponse.getString("status");

                            if ("success".equals(status)) {
                                String primaLettera = String.valueOf(username.charAt(0));
                                Intent intent = null;

                                switch (primaLettera) {
                                    case "s":
                                        for (Studente s: studenti){
                                            if( s.getCredenziali().getUser().equals(username)){
                                                intent = new Intent(MainActivity.this, studenteActivity.class);
                                                intent.putExtra("studente", s);
                                            }
                                        }
                                        break;
                                    case "g":
                                        for (Genitore g : genitori){
                                            if(g.getCredenziali().getUser().equals(username)){
                                                intent = new Intent(MainActivity.this, genitoreActivity.class);
                                                intent.putExtra("genitore", g);
                                            }
                                        }
                                        break;
                                    case "d":
                                        for (Docente d : docenti){
                                            if(d.getCredenziali().getUser().equals(username)){
                                                intent = new Intent(MainActivity.this, docenteActivity.class);
                                                intent.putExtra("docente", d);
                                            }
                                        }
                                        break;
                                    default:
                                        errore.setVisibility(View.VISIBLE);
                                        errore.setText("Ruolo non riconosciuto!");
                                        return;
                                }
                                startActivity(intent);
                                usernameText.setText("");
                                passwordText.setText("");
                            } else {
                                String message = jsonResponse.optString("message", "Errore sconosciuto");
                                errore.setVisibility(View.VISIBLE);
                                errore.setText(message);
                            }
                        } catch (JSONException e) {
                            errore.setVisibility(View.VISIBLE);
                            errore.setText("Errore nel parsing della risposta del server.");
                            Log.e("JSON_ERROR", e.getMessage(), e);
                        }
                    });
                } else {
                    runOnUiThread(() -> {
                        errore.setVisibility(View.VISIBLE);
                        errore.setText("Errore del server: " + response.message());
                        Toast.makeText(MainActivity.this, "Errore dal server", Toast.LENGTH_SHORT).show();
                    });
                }
            }
        });
    }


    public ArrayList<Studente> getStudentiServer(){
        OkHttpClient client = new OkHttpClient();
        String responseBody = "";
        Request r = new Request.Builder().url(STUDENTI_URL).get().build();

        try {
            Response response = client.newCall(r).execute();

            // Mostra la risposta nella console
            if (response.isSuccessful()) {
                 responseBody = response.body().string();
            } else {
                 responseBody = response.body().string();
                System.out.println("errore: " + responseBody);
            }
        } catch (IOException ex) {
            System.out.println("Eccezione: " + ex.getMessage());
        }
        studenti = new ArrayList<>();

        String s = responseBody;
        String[] s2 = s.split("\\[");
        if(s2.length<2){
            return studenti;
        }
        String[] s3 = s2[1].split("]");

        String[] studentiSplit = s3[0].split("\\{");

        for (String tmp: studentiSplit) {
            tmp = tmp.split("}")[0];
            String[] temp_split = tmp.split(",");
            if(temp_split.length>1) {
                String username = temp_split[0].split(":")[1].substring(1);
                username = username.substring(0,username.length()-1);
                String password = temp_split[1].split(":")[1].substring(1);
                password = password.substring(0,password.length()-1);
                String nome = temp_split[2].split(":")[1].substring(1);
                nome = nome.substring(0,nome.length()-1);
                String cognome = temp_split[3].split(":")[1].substring(1);
                cognome = cognome.substring(0,cognome.length()-1);
                //data
                String dataString = temp_split[4].split(":")[1].substring(1);
                dataString = dataString.substring(0,dataString.length()-1);
                int giorno = Integer.parseInt(dataString.split("-")[0]);
                int mese = Integer.parseInt(dataString.split("-")[1]);
                int anno = Integer.parseInt(dataString.split("-")[2]);
                Calendar c = Calendar.getInstance();
                c.set(anno, mese, giorno);
                Date data = c.getTime();

                String cf = temp_split[5].split(":")[1].substring(1);
                cf = cf.substring(0,cf.length()-1);

                String classeStringa = temp_split[6].split(":")[1].substring(1);
                classeStringa = classeStringa.substring(0,classeStringa.length()-1);
                Classe classe = null;
                for (Classe x: getClassi()) {
                    if(x.toString().equals(classeStringa)){
                        classe = x;
                    }
                }
                //voti
                String votiStringa = temp_split[7].split(":")[1].substring(1);
                votiStringa = votiStringa.substring(0,votiStringa.length()-1);
                ArrayList<Voti> voti = new ArrayList<>();

                for (String v: votiStringa.split(";")) {
                    if(!v.isEmpty()){

                        double valore = Double.parseDouble(v.split(" ")[0]);
                        String materia = v.split(" ")[1];
                        String nomeDoc = v.split(" ")[2].split("_")[0];
                        String cognomeDoc = v.split(" ")[2].split("_")[1];
                        Docente docente = null;
                        for (Docente d: getDocenti()) {
                            if(nomeDoc.equals(d.getNome()) && cognomeDoc.equals(d.getCognome())){
                                docente = d;
                                break;
                            }
                        }
                        String dataVoto = v.split(" ")[3];
                        dataVoto = dataVoto.substring(0,dataVoto.length()-1);
                        int gv = Integer.parseInt(dataVoto.split("-")[0]);
                        int mv = Integer.parseInt(dataVoto.split("-")[1]);
                        int av = Integer.parseInt(dataVoto.split("-")[2]);
                        Calendar cv = Calendar.getInstance();
                        cv.set(av, mv, gv);
                        Date datavoto = cv.getTime();
                        voti.add(new Voti(valore,materia,docente,datavoto));
                    }
                }

                //note
                String noteStringa = temp_split[8].split(":")[1].substring(1);
                noteStringa = noteStringa.substring(0,noteStringa.length()-1);
                ArrayList<Note> note = new ArrayList<>();
                for (String n: noteStringa.split(";")) {
                    if(!n.isEmpty()){
                        String dataString2 = n.split("\\*")[0];
                        int gv = Integer.parseInt(dataString2.split("-")[0]);
                        int mv = Integer.parseInt(dataString2.split("-")[1]);
                        int av = Integer.parseInt(dataString2.split("-")[2]);
                        Calendar cv = Calendar.getInstance();
                        cv.set(av, mv, gv);
                        Date dataNota = cv.getTime();
                        String docenteNome = n.split("\\*")[1].split("_")[0];
                        String docenteCognome = n.split("\\*")[1].split("_")[1];
                        Docente docente = null;
                        for (Docente d: getDocenti()) {
                            if(docenteNome.equals(d.getNome()) && docenteCognome.equals(d.getCognome())){
                                docente = d;
                                break;
                            }
                        }
                        String motivo = n.split("\\*")[2].split("_")[1].trim();
                        note.add(new Note(dataNota,docente,motivo));
                    }
                }

                //assenze
                String assenzeStringa = temp_split[9].split(":")[1].substring(1);
                assenzeStringa = assenzeStringa.substring(0,assenzeStringa.length()-1);
                ArrayList<Assenza> assenze = new ArrayList<>();
                for (String a: assenzeStringa.split(";")) {
                    if(!a.isEmpty()){
                        String dataAssenza = a.split(" ")[0];
                        int gv = Integer.parseInt(dataAssenza.split("-")[0]);
                        int mv = Integer.parseInt(dataAssenza.split("-")[1]);
                        int av = Integer.parseInt(dataAssenza.split("-")[2]);
                        Calendar cv = Calendar.getInstance();
                        cv.set(av, mv, gv);
                        Date dataNota = cv.getTime();

                        String docenteNome = a.split(" ")[1].split("_")[0];
                        String docenteCognome = a.split(" ")[1].split("_")[1];
                        Docente docente = null;
                        for (Docente d: getDocenti()) {
                            if(docenteNome.equals(d.getNome()) && docenteCognome.equals(d.getCognome())){
                                docente = d;
                                break;
                            }
                        }
                        String giustifica = a.split(" ")[2];
                        if(giustifica.equals("true")){
                            assenze.add(new Assenza(docente,dataNota,true));
                        }
                        else{
                            assenze.add(new Assenza(docente,dataNota,false));
                        }
                    }
                }
                Studente studente = new Studente(nome,cognome,data,cf,classe);
                studente.setCredenziali(new Credenziali(username,password));
                studente.setVoti(voti);
                studente.setNote(note);
                studente.setAssenze(assenze);

                studenti.add(studente);
            }
        }
        return studenti;
    }

    /**
     * Restituisce lista delle classi registrate.
     *
     * @return Lista delle classi registrate.
     */
    public ArrayList<Classe> getClassiServer(){
        OkHttpClient client = new OkHttpClient();

        Request r = new Request.Builder().url(CLASSI_URL).get().build();
        String responseBody = "";
        try {
            Response response = client.newCall(r).execute();

            // Mostra la risposta nella console
            if (response.isSuccessful()) {
                responseBody = response.body().string();
            } else {
                 responseBody = response.body().string();
            }
        } catch (IOException ex) {
            // Gestisci gli errori di connessione
            System.out.println("Eccezione: " + ex.getMessage());
        }

        classi = new ArrayList<>();

        String c = responseBody;
        String[] s2 = c.split("\\[");
        if(s2.length<2){
            return classi;
        }
        String[] s3 = s2[1].split("]");

        String[] classiSplit = s3[0].split("\\{");

        for (String tmp: classiSplit) {
            tmp = tmp.split("}")[0];
            String[] temp_split = tmp.split(",");
            if(temp_split.length>1){
                int anno = Integer.parseInt(String.valueOf(temp_split[0].split(":")[1].charAt(1)));
                String indirizzo = temp_split[1].split(":")[1].substring(1,4);
                char sezione = temp_split[2].split(":")[1].charAt(1);
                Classe cl = new Classe(anno,indirizzo,sezione);
                classi.add(cl);
            }
        }
        return classi;
    }

    /**
     * Restituisce lista dei genitori registrati.
     *
     * @return Lista dei genitori registrati.
     */
    public ArrayList<Genitore> getGenitoriServer(){
        OkHttpClient client = new OkHttpClient();
        String responseBody = "";
        Request r = new Request.Builder().url(GENITORI_URL).get().build();

        try {
            Response response = client.newCall(r).execute();

            // Mostra la risposta nella console
            if (response.isSuccessful()) {
                 responseBody = response.body().string();
            } else {
                 responseBody = response.body().string();
            }
        } catch (IOException ex) {
            // Gestisci gli errori di connessione
            System.out.println("Eccezione: " + ex.getMessage());
        }
        String s = responseBody;
        genitori = new ArrayList<>();

        String[] s2 = s.split("\\[");

        if(s2.length<2){
            return genitori;
        }

        String[] s3 = s2[1].split("]");
        String[] genitoriSplit = s3[0].split("\\{");

        for (String tmp: genitoriSplit) {
            tmp = tmp.split("}")[0];
            String[] temp_split = tmp.split(",");
            if(temp_split.length>1){
                String username = temp_split[0].split(":")[1].substring(1);
                username = username.substring(0,username.length()-1);

                String password = temp_split[1].split(":")[1].substring(1);
                password = password.substring(0,password.length()-1);
                String nome = temp_split[2].split(":")[1].substring(1);
                nome = nome.substring(0,nome.length()-1);
                String cognome = temp_split[3].split(":")[1].substring(1);
                cognome = cognome.substring(0,cognome.length()-1);
                //data
                String dataString = temp_split[4].split(":")[1].substring(1);
                dataString = dataString.substring(0,dataString.length()-1);
                int giorno = Integer.parseInt(dataString.split("-")[0]);
                int mese = Integer.parseInt(dataString.split("-")[1]);
                int anno = Integer.parseInt(dataString.split("-")[2]);
                Calendar c = Calendar.getInstance();
                c.set(anno, mese, giorno);
                Date data = c.getTime();

                String cf = temp_split[5].split(":")[1].substring(1);
                cf = cf.substring(0,cf.length()-1);
                //cf figlio
                String cfFiglio = temp_split[6].split(":")[1].substring(1);
                cfFiglio = cfFiglio.substring(0,cfFiglio.length()-1);
                Studente figlio = null;
                for (Studente st: getStudenti()) {
                    if(cfFiglio.equals(st.getCF())){
                        figlio = st;
                        break;
                    }
                }

                Genitore g = new Genitore(nome,cognome,data,cf,figlio);
                g.setCredenziali(new Credenziali(username,password));
                genitori.add(g);
            }
        }
        return genitori;
    }

    /**
     * Restituisce lista dei docenti registrati.
     *
     * @return Lista dei docenti registrati.
     */
    public ArrayList<Docente> getDocentiServer(){
        OkHttpClient client = new OkHttpClient();

        Request r = new Request.Builder().url(DOCENTI_URL).get().build();
        String responseBody = "";
        try {
            Response response = client.newCall(r).execute();

            // Mostra la risposta nella console
            if (response.isSuccessful()) {
                 responseBody = response.body().string();
            } else {
                 responseBody = response.body().string();
            }
        } catch (IOException ex) {
            // Gestisci gli errori di connessione
            System.out.println("Eccezione: " + ex.getMessage());
        }

        String s = responseBody;

        docenti = new ArrayList<>();

        String[] s2 = s.split("\\[");

        if(s2.length<2){
            return docenti;
        }

        String[] s3 = s2[1].split("]");

        String[] docentiSplit = s3[0].split("\\{");
        for (String tmp: docentiSplit) {
            tmp = tmp.split("}")[0];
            String[] temp_split = tmp.split(",");
            if(temp_split.length>1){
                String username = temp_split[0].split(":")[1].substring(1);
                username = username.substring(0,username.length()-1);

                String password = temp_split[1].split(":")[1].substring(1);
                password = password.substring(0,password.length()-1);
                String nome = temp_split[2].split(":")[1].substring(1);
                nome = nome.substring(0,nome.length()-1);
                String cognome = temp_split[3].split(":")[1].substring(1);
                cognome = cognome.substring(0,cognome.length()-1);
                //data
                String dataString = temp_split[4].split(":")[1].substring(1);
                dataString = dataString.substring(0,dataString.length()-1);
                int giorno = Integer.parseInt(dataString.split("-")[0]);
                int mese = Integer.parseInt(dataString.split("-")[1]);
                int anno = Integer.parseInt(dataString.split("-")[2]);
                Calendar c = Calendar.getInstance();
                c.set(anno, mese, giorno);
                Date data = c.getTime();

                String cf = temp_split[5].split(":")[1].substring(1);
                cf = cf.substring(0,cf.length()-1);

                //array
                ArrayList<Classe> classi = new ArrayList<>();
                String classiString = temp_split[6].split(":")[1].substring(1);
                classiString = classiString.substring(0,classiString.length()-1);
                for (String classe: classiString.split(";")) {
                    if(!classe.isEmpty()){
                        for (Classe x: getClassi()) {
                            if(x.toString().equals(classe)){
                                classi.add(x);
                            }
                        }
                    }
                }

                ArrayList<String> materie = new ArrayList<>();
                String materieString = temp_split[7].split(":")[1].substring(1);
                materieString = materieString.substring(0,materieString.length()-1);
                for (String materia: materieString.split(";")) {
                    if(!materia.isEmpty()){
                        materie.add(materia);
                    }
                }

                Docente docente = new Docente(nome,cognome,data,cf,classi,materie);
                docente.setCredenziali(new Credenziali(username,password));
                docenti.add(docente);
            }
        }
        return docenti;
    }

    public ArrayList<Studente> getStudenti() {
        return studenti;
    }
    public ArrayList<Docente> getDocenti() {
        return docenti;
    }
    public ArrayList<Genitore> getGenitori() {
        return genitori;
    }
    public ArrayList<Classe> getClassi() {
        return classi;
    }
}
