package com.example.habittracker;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AddHabitActivity extends AppCompatActivity {

    private EditText habitNameEditText, habitDescriptionEditText;
    private Button saveHabitButton;
    private DatabaseHelper databaseHelper;
    private String currentUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit);

        habitNameEditText = findViewById(R.id.habitNameEditText);
        habitDescriptionEditText = findViewById(R.id.habitDescriptionEditText);
        saveHabitButton = findViewById(R.id.saveHabitButton);

        databaseHelper = new DatabaseHelper(this);
        currentUsername = getIntent().getStringExtra("username");

        saveHabitButton.setOnClickListener(v -> {
            String habitName = habitNameEditText.getText().toString().trim();
            String habitDescription = habitDescriptionEditText.getText().toString().trim();

            if (habitName.isEmpty()) {
                Toast.makeText(this, "Por favor ingrese el nombre del hábito", Toast.LENGTH_SHORT).show();
                return;
            }

            // Agrega el hábito a la base de datos
            databaseHelper.addHabit(habitName, habitDescription, currentUsername);
            Toast.makeText(this, "Hábito guardado exitosamente", Toast.LENGTH_SHORT).show();
            finish();  // Regresa a MainActivity
        });
    }
}



