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
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static final String SERVER_URL = "https://tommasomazzoni.altervista.org/WS.php";
    private EditText usernameText;
    private EditText passwordText;
    private Button accediButton;
    private TextView errore;
    private String username;

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
                .url(SERVER_URL)
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                runOnUiThread(() -> {
                    errore.setVisibility(View.VISIBLE);
                    errore.setText("Errore di connessione: " + e.getMessage());
                    Toast.makeText(MainActivity.this, "Errore di connessione", Toast.LENGTH_SHORT).show();
                });
                Log.e("HTTP_ERROR", e.getMessage(), e);
            }

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
                                Intent intent;

                                switch (primaLettera) {
                                    case "s":
                                        intent = new Intent(MainActivity.this, studenteActivity.class);
                                        intent.putExtra("username", username);
                                        break;
                                    case "g":
                                        intent = new Intent(MainActivity.this, genitoreActivity.class);
                                        intent.putExtra("username", username);
                                        break;
                                    case "d":
                                        intent = new Intent(MainActivity.this, docenteActivity.class);
                                        intent.putExtra("username", username);
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
}
