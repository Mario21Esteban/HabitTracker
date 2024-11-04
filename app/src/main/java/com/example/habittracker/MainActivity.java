package com.example.habittracker;

import android.os.Bundle;
import android.widget.ProgressBar;

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
    private DatabaseHelper databaseHelper;
    private String currentUsername;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerViewHabits);
        progressBar = findViewById(R.id.progressBar);

        databaseHelper = new DatabaseHelper(this);
        currentUsername = getIntent().getStringExtra("username");

        loadHabits();
    }

    private void loadHabits() {
        List<Habit> habits = databaseHelper.getHabitsForUser(currentUsername);
        habitAdapter = new HabitAdapter(habits, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(habitAdapter);

        // Lógica para calcular y actualizar el progreso
        int completedHabits = (int) habits.stream().filter(Habit::isCompleted).count();
        progressBar.setProgress(completedHabits * 100 / habits.size());
    }
}
