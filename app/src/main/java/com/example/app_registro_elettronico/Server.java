package com.example.app_registro_elettronico;

import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.example.app_registro_elettronico.gestione.*;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Server {

    private static final String STUDENTI_URL = "https://tommasomazzoni.altervista.org/studente.php";
    private static final String DOCENTI_URL = "https://tommasomazzoni.altervista.org/docente.php";
    private static final String GENITORI_URL = "https://tommasomazzoni.altervista.org/genitore.php";
    private static final String CLASSI_URL = "https://tommasomazzoni.altervista.org/classe.php";

    private ArrayList<Studente> studenti = new ArrayList<>();
    private ArrayList<Docente> docenti= new ArrayList<>();
    private ArrayList<Genitore> genitori= new ArrayList<>();
    private ArrayList<Classe> classi = new ArrayList<>();

    public interface CallbackStudenti {
        void onSuccess(ArrayList<Studente> studenti);
        void onFailure(Exception e);
    }

    public ArrayList<Studente> getStudentiServer() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(STUDENTI_URL)
                .get()
                .build();

        try {
            Response response = client.newCall(request).execute();

            if (response.isSuccessful() && response.body() != null) {
                Log.d("DEBUG", "Risposta ricevuta dal server");
                String responseBody = response.body().string();
                return handleStudentResponse(responseBody);
            } else {
                Log.e("HTTP_ERROR", "Risposta non valida dal server");
            }
        } catch (IOException e) {
            Log.e("HTTP_ERROR", "Errore nella richiesta HTTP: " + e.getMessage(), e);
        }

        return new ArrayList<>(); // Ritorna una lista vuota in caso di errore
    }

    private ArrayList<Studente> handleStudentResponse(String responseBody) {
        ArrayList<Studente> studenti = new ArrayList<>();

        try {
            JSONObject jsonResponse = new JSONObject(responseBody);
            String status = jsonResponse.getString("status");

            if ("success".equals(status)) {
                JSONArray jsonStudenti = jsonResponse.getJSONArray("studenti");

                for (int i = 0; i < jsonStudenti.length(); i++) {
                    JSONObject jsonStudente = jsonStudenti.getJSONObject(i);

                    // Parsing dati studente
                    String username = jsonStudente.getString("username");
                    String password = jsonStudente.getString("password");
                    String nome = jsonStudente.getString("nome");
                    String cognome = jsonStudente.getString("cognome");
                    String dataString = jsonStudente.getString("data");
                    String cf = jsonStudente.getString("codiceFiscale");
                    String classeStringa = jsonStudente.getString("classe");

                    // Parsing della data
                    Date dataNascita = parseData(dataString);

                    // Trova la classe
                    Classe classe = new Classe(
                            Integer.parseInt(String.valueOf(classeStringa.charAt(0))),
                            classeStringa.substring(2, 4),
                            classeStringa.charAt(1)
                    );

                    // Parsing voti, note e assenze
                    ArrayList<Voti> voti = parseVoti(jsonStudente.getString("voti"));
                    ArrayList<Note> note = parseNote(jsonStudente.getString("note"));
                    ArrayList<Assenza> assenze = parseAssenze(jsonStudente.getString("assenze"));

                    // Crea l'oggetto Studente
                    Studente studente = new Studente(nome, cognome, dataNascita, cf, classe);
                    studente.setCredenziali(new Credenziali(username, password));
                    studente.setVoti(voti);
                    studente.setNote(note);
                    studente.setAssenze(assenze);

                    studenti.add(studente);
                }
            }
        } catch (JSONException e) {
            Log.e("JSONException", "Errore durante il parsing del JSON: " + e.getMessage(), e);
        }

        return studenti;
    }

    private Date parseData(String dataString) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("d-M-yyyy", Locale.getDefault());
            return sdf.parse(dataString);
        } catch (ParseException e) {
            Log.e("DataParsingError", "Errore nel parsing della data: " + dataString);
            return null;
        }
    }

    private ArrayList<Voti> parseVoti(String votiStringa) {
        ArrayList<Voti> voti = new ArrayList<>();

        if (votiStringa == null || votiStringa.isEmpty()) {
            return voti;
        }

        try {
            String[] votiArray = votiStringa.split(";");

            for (String voto : votiArray) {
                if (!voto.isEmpty()) {
                    String[] votoParts = voto.split(" ");

                    double valore = Double.parseDouble(votoParts[0]);
                    String materia = votoParts[1];

                    // Docente
                    String[] docenteParts = votoParts[2].split("_");
                    String nomeDocente = docenteParts[0];
                    String cognomeDocente = docenteParts[1];
                    Docente docente = getDocenteByName(nomeDocente, cognomeDocente);

                    // Data del voto
                    String dataString = votoParts[3];
                    Date dataVoto = parseData(dataString);

                    voti.add(new Voti(valore, materia, docente, dataVoto));
                }
            }
        } catch (Exception e) {
            Log.e("ParseVotiError", "Errore durante il parsing dei voti: " + e.getMessage(), e);
        }

        return voti;
    }

    private ArrayList<Note> parseNote(String noteStringa) {
        ArrayList<Note> note = new ArrayList<>();

        if (noteStringa == null || noteStringa.isEmpty()) {
            return note;
        }

        try {
            String[] noteArray = noteStringa.split(";");

            for (String nota : noteArray) {
                if (!nota.isEmpty()) {
                    String[] notaParts = nota.split("\\*");

                    // Data della nota
                    String dataString = notaParts[0];
                    Date dataNota = parseData(dataString);

                    // Docente
                    String[] docenteParts = notaParts[1].split("_");
                    String nomeDocente = docenteParts[0];
                    String cognomeDocente = docenteParts[1];
                    Docente docente = getDocenteByName(nomeDocente, cognomeDocente);

                    // Motivo
                    String motivo = notaParts[2];

                    note.add(new Note(dataNota, docente, motivo));
                }
            }
        } catch (Exception e) {
            Log.e("ParseNoteError", "Errore durante il parsing delle note: " + e.getMessage(), e);
        }

        return note;
    }

    private ArrayList<Assenza> parseAssenze(String assenzeStringa) {
        ArrayList<Assenza> assenze = new ArrayList<>();

        if (assenzeStringa == null || assenzeStringa.isEmpty()) {
            return assenze;
        }

        try {
            String[] assenzeArray = assenzeStringa.split(";");

            for (String assenza : assenzeArray) {
                if (!assenza.isEmpty()) {
                    String[] assenzaParts = assenza.split(" ");

                    // Data dell'assenza
                    String dataString = assenzaParts[0];
                    Date dataAssenza = parseData(dataString);

                    // Docente
                    String[] docenteParts = assenzaParts[1].split("_");
                    String nomeDocente = docenteParts[0];
                    String cognomeDocente = docenteParts[1];
                    Docente docente = getDocenteByName(nomeDocente, cognomeDocente);

                    // Giustificazione
                    boolean giustificata = Boolean.parseBoolean(assenzaParts[2]);

                    assenze.add(new Assenza(docente, dataAssenza, giustificata));
                }
            }
        } catch (Exception e) {
            Log.e("ParseAssenzeError", "Errore durante il parsing delle assenze: " + e.getMessage(), e);
        }

        return assenze;
    }

    private Docente getDocenteByName(String nome, String cognome) {
        for (Docente docente : getDocentiServer()) {
            if (docente.getNome().equals(nome) && docente.getCognome().equals(cognome)) {
                return docente;
            }
        }
        return null;
    }

    private Classe getClasseByString(String classeStringa) {
        for (Classe classe : getClassiServer()) {
            if (classe.toString().equals(classeStringa)) {
                return classe;
            }
        }
        return null;
    }

    public ArrayList<Classe> getClassiServer() {
        OkHttpClient client = new OkHttpClient();
        String responseBody = "";

        Request request = new Request.Builder()
                .url(CLASSI_URL)
                .get()
                .build();

        try {
            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                responseBody = response.body().string();
            } else {
                Log.e("HttpError", "Errore durante la richiesta delle classi");
            }
        } catch (IOException e) {
            Log.e("IOException", "Errore durante la connessione: " + e.getMessage(), e);
        }

        try {
            JSONArray jsonClassi = new JSONArray(responseBody);

            for (int i = 0; i < jsonClassi.length(); i++) {
                JSONObject jsonClasse = jsonClassi.getJSONObject(i);

                String nomeClasse = jsonClasse.getString("nome");
                int idClasse = jsonClasse.getInt("id");
                int anno = jsonClasse.getInt("anno");
                String indirizzo = jsonClasse.getString("indirizzo");
                char sezione = jsonClasse.getString("sezione").charAt(0);

                Classe classe = new Classe(anno, indirizzo, sezione);
                classi.add(classe);
            }
        } catch (JSONException e) {
            Log.e("JSONException", "Errore durante il parsing del JSON delle classi: " + e.getMessage(), e);
        }

        return classi;
    }

    public ArrayList<Docente> getDocentiServer() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(DOCENTI_URL)
                .get()
                .build();

        try {
            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                handleDocenteResponse(responseBody);
            } else {
                Log.e("HttpError", "Errore durante la richiesta dei docenti");
            }
        } catch (IOException e) {
            Log.e("IOException", "Errore durante la connessione: " + e.getMessage(), e);
        }

        return docenti;
    }

    private void handleDocenteResponse(String responseBody) {
        try {
            JSONObject jsonResponse = new JSONObject(responseBody);
            JSONArray jsonDocenti = jsonResponse.getJSONArray("docenti");
            Log.d("docente sus", jsonDocenti.toString());

            for (int i = 0; i < jsonDocenti.length(); i++) {
                JSONObject jsonDocente = jsonDocenti.getJSONObject(i);

                String nome = jsonDocente.getString("nome");
                String cognome = jsonDocente.getString("cognome");
                String materia = jsonDocente.getString("materie");
                String classi = jsonDocente.getString("classi") ;
                ArrayList<Classe> clas= new ArrayList<>();
                String[] damn = classi.split(";");
                for (String s : damn) {
                    clas.add(new Classe(Integer.parseInt(String.valueOf(s.charAt(0))),s.substring(2,4),s.charAt(1)));
                }

                ArrayList<String> materie= new ArrayList<>();
                String[] sus = materia.split(";");
                for (String s : sus) {
                    materie.add(s);
                }
                Docente docente = new Docente(nome, cognome, parseData(jsonDocente.getString("data")), jsonDocente.getString("codiceFiscale"), clas , materie);
                docenti.add(docente);
            }
        } catch (JSONException e) {
            Log.e("JSONException", "Errore durante il parsing del JSON dei docenti: " + e.getMessage(), e);
        }
    }

    private ArrayList<Classe> getClassiFromJson(JSONArray jsonClassi) throws JSONException {
        ArrayList<Classe> classi = new ArrayList<>();
        for (int i = 0; i < jsonClassi.length(); i++) {
            JSONObject jsonClasse = jsonClassi.getJSONObject(i);
            int anno = jsonClasse.getInt("anno");
            String indirizzo = jsonClasse.getString("indirizzo");
            char sezione = jsonClasse.getString("sezione").charAt(0);

            classi.add(new Classe(anno, indirizzo, sezione));
        }
        return classi;
    }

    private ArrayList<String> parseMaterie(JSONArray jsonMaterie) throws JSONException {
        ArrayList<String> materie = new ArrayList<>();
        for (int i = 0; i < jsonMaterie.length(); i++) {
            materie.add(jsonMaterie.getString(i));
        }
        return materie;
    }
}
