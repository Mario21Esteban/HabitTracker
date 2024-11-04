package com.example.habittracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private RadioGroup radioGroup;
    private Button loginButton;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.usernameEditText);
        radioGroup = findViewById(R.id.radioGroup);
        loginButton = findViewById(R.id.loginButton);
        databaseHelper = new DatabaseHelper(this);

        loginButton.setOnClickListener(v -> handleLoginOrRegister());
    }

    private void handleLoginOrRegister() {
        String username = usernameEditText.getText().toString().trim();
        int selectedOption = radioGroup.getCheckedRadioButtonId();

        if (username.isEmpty()) {
            Toast.makeText(this, "Por favor ingrese un nombre de usuario", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedOption == R.id.rb_login) {
            if (databaseHelper.checkUser(username)) {
                // Usuario encontrado, inicia MainActivity
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Usuario no encontrado. Regístrese primero.", Toast.LENGTH_SHORT).show();
            }
        } else if (selectedOption == R.id.rb_register) {
            if (databaseHelper.checkUser(username)) {
                Toast.makeText(this, "El usuario ya existe. Intente iniciar sesión.", Toast.LENGTH_SHORT).show();
            } else {
                databaseHelper.addUser(username);
                Toast.makeText(this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show();
            }
        }
    }

    

}


