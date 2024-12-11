package com.example.app_registro_elettronico;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText usernameText;
    private EditText passwordText;
    private Button accediButton;
    private TextView errore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView titoloTextView = findViewById(R.id.labelRegistroElettronico);
        usernameText = findViewById(R.id.Username);
        passwordText = findViewById(R.id.Password);
        accediButton = findViewById(R.id.accedi);
        errore = findViewById(R.id.errorMessage);

        accediButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameText.getText().toString().trim();
                String password = passwordText.getText().toString().trim();
                if (username.isEmpty() || password.isEmpty()) {
                    errore.setVisibility(View.VISIBLE);
                } else {
                    //apro il file e cerco quella persona, se è presente mi salvo la persona
                    if(true){
                        errore.setVisibility(View.GONE);
                        int sus=1;

                        if(sus == 1){
                            //studente
                                Intent intent = new Intent(MainActivity.this, studenteActivity.class);
                                startActivity(intent);
                        } else if (sus==2) {
                            //genitore
                            Intent intent= new Intent(MainActivity.this, genitoreActivity.class);
                            startActivity(intent);
                        }else{
                            //docente
                            Intent intent= new Intent(MainActivity.this, docenteActivity.class);
                            startActivity(intent);
                        }
                        usernameText.setText("");
                        passwordText.setText("");
                        finish();
                    }else{
                        errore.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }
}
