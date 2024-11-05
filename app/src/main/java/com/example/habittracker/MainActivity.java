package com.example.habittracker;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HabitAdapter habitAdapter;
    private DatabaseHelper databaseHelper;  // Declarar la variable
    private String currentUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializa el RecyclerView y el DatabaseHelper
        recyclerView = findViewById(R.id.recyclerViewHabits);
        databaseHelper = new DatabaseHelper(this);  // Asegúrate de inicializar aquí

        // Obtén el nombre de usuario del Intent
        currentUsername = getIntent().getStringExtra("username");

        if (currentUsername == null || currentUsername.isEmpty()) {
            // Si no se recibió el username, muestra un mensaje y cierra la actividad
            Toast.makeText(this, "Error: Nombre de usuario no recibido", Toast.LENGTH_SHORT).show();
            finish();  // Cierra la actividad
            return;
        }

        loadHabits(currentUsername);  // Cargar hábitos para el usuario actual
    }

    private void loadHabits(String username) {
        int userId = databaseHelper.getUserId(username);

        if (userId == -1) {
            Toast.makeText(this, "Error: Usuario no encontrado", Toast.LENGTH_SHORT).show();
            finish();  // Cierra la actividad si el usuario no existe
            return;
        }

        List<Habit> habits = databaseHelper.getHabitsForUser(username);
        habitAdapter = new HabitAdapter(habits, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(habitAdapter);
    }
}

