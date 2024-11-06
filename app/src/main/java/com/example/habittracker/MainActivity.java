package com.example.habittracker;

import android.content.Intent;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HabitAdapter habitAdapter;
    private DatabaseHelper databaseHelper;
    private String currentUsername;
    private FloatingActionButton fabAddHabit;  // Botón para agregar hábitos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerViewHabits);
        fabAddHabit = findViewById(R.id.fabAddHabit);  // Inicializa el botón
        databaseHelper = new DatabaseHelper(this);

        currentUsername = getIntent().getStringExtra("username");

        if (currentUsername == null || currentUsername.isEmpty()) {
            Toast.makeText(this, "Error: Nombre de usuario no recibido", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Cargar los hábitos guardados
        loadHabits(currentUsername);

        // Configura el FAB para agregar un nuevo hábito
        fabAddHabit.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddHabitActivity.class);
            intent.putExtra("username", currentUsername);  // Pasar el username
            startActivity(intent);
        });
    }

    private void loadHabits(String username) {
        List<Habit> habits = databaseHelper.getHabitsForUser(username);
        habitAdapter = new HabitAdapter(habits, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(habitAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Recargar los hábitos cuando se regrese a esta actividad
        loadHabits(currentUsername);
    }
}
