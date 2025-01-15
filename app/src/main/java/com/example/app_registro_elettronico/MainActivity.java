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

    private EditText usernameText;
    private EditText passwordText;
    private Button accediButton;
    private TextView errore;
    private String username;

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
                sendRequest( username, password);
            }
        });
    }

    /**
     * Metodo per inviare richieste HTTP al server.
     * @param username Username dell'utente.
     * @param password Password dell'utente.
     */
    private void sendRequest(String username, String password) {
        OkHttpClient client = new OkHttpClient();
        FormBody formBody = new FormBody.Builder()
                .add("azione", "login")
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
                                                intent = new Intent(MainActivity.this, studenteActivity.class);
                                                intent.putExtra("username", username);
                                                intent.putExtra("password", password);
                                        break;
                                    case "g":
                                                intent = new Intent(MainActivity.this, genitoreActivity.class);
                                                intent.putExtra("username", username);
                                                intent.putExtra("password", password);
                                        break;
                                    case "d":
                                                intent = new Intent(MainActivity.this, docenteActivity.class);
                                                intent.putExtra("username", username);
                                                intent.putExtra("password", password);
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
                        } catch (Exception e) {
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
}


