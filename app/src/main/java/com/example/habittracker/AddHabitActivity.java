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
    private RatingBar ratingBarPriority;
    private Button saveHabitButton, setNotificationButton;
    private Calendar notificationCalendar;
    private DatabaseHelper databaseHelper;
    private String currentUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit);

        habitNameEditText = findViewById(R.id.habitNameEditText);
        habitDescriptionEditText = findViewById(R.id.habitDescriptionEditText);
        ratingBarPriority = findViewById(R.id.ratingBarPriority);
        saveHabitButton = findViewById(R.id.saveHabitButton);
        setNotificationButton = findViewById(R.id.setNotificationButton);

        databaseHelper = new DatabaseHelper(this);
        currentUsername = getIntent().getStringExtra("username");

        // Configurar el botón para programar notificación
        setNotificationButton.setOnClickListener(v -> openDateTimePicker());

        // Guardar el hábito en la base de datos
        saveHabitButton.setOnClickListener(v -> saveHabit());
    }

    private void openDateTimePicker() {
        notificationCalendar = Calendar.getInstance();

        // Seleccionar fecha
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            notificationCalendar.set(Calendar.YEAR, year);
            notificationCalendar.set(Calendar.MONTH, month);
            notificationCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            // Seleccionar hora
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view1, hourOfDay, minute) -> {
                notificationCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                notificationCalendar.set(Calendar.MINUTE, minute);
                Toast.makeText(this, "Notificación programada", Toast.LENGTH_SHORT).show();

            }, notificationCalendar.get(Calendar.HOUR_OF_DAY), notificationCalendar.get(Calendar.MINUTE), true);

            timePickerDialog.show();
        }, notificationCalendar.get(Calendar.YEAR), notificationCalendar.get(Calendar.MONTH), notificationCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    private void saveHabit() {
        String habitName = habitNameEditText.getText().toString().trim();
        String habitDescription = habitDescriptionEditText.getText().toString().trim();
        int priority = (int) ratingBarPriority.getRating();

        if (habitName.isEmpty()) {
            Toast.makeText(this, "Ingrese un nombre para el hábito", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear y guardar el hábito
        databaseHelper.addHabit(habitName, habitDescription, priority, currentUsername);
        Toast.makeText(this, "Hábito guardado", Toast.LENGTH_SHORT).show();
        finish();
    }
}

