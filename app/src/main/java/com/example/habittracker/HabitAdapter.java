package com.example.habittracker;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HabitAdapter extends RecyclerView.Adapter<HabitAdapter.HabitViewHolder> {

    private List<Habit> habitList;
    private Context context;
    private DatabaseHelper databaseHelper;  // Instancia de DatabaseHelper

    public HabitAdapter(List<Habit> habitList, Context context) {
        this.habitList = habitList;
        this.context = context;
        this.databaseHelper = new DatabaseHelper(context);  // Inicializar DatabaseHelper
    }

    @NonNull
    @Override
    public HabitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_habit, parent, false);
        return new HabitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HabitViewHolder holder, int position) {
        Habit habit = habitList.get(position);
        holder.habitNameTextView.setText(habit.getName());
        holder.habitDescriptionTextView.setText(habit.getDescription());
        holder.checkBoxCompleted.setChecked(habit.isCompleted());

        // Marcar hábito como completado y actualizar en la base de datos
        holder.checkBoxCompleted.setOnCheckedChangeListener((buttonView, isChecked) -> {
            habit.setCompleted(isChecked);
            databaseHelper.updateHabitCompletion(habit.getId(), isChecked);  // Actualización con databaseHelper
        });
    }

    @Override
    public int getItemCount() {
        return habitList.size();
    }

    public static class HabitViewHolder extends RecyclerView.ViewHolder {
        TextView habitNameTextView, habitDescriptionTextView;
        CheckBox checkBoxCompleted;

        public HabitViewHolder(@NonNull View itemView) {
            super(itemView);
            habitNameTextView = itemView.findViewById(R.id.habitNameTextView);
            habitDescriptionTextView = itemView.findViewById(R.id.habitDescriptionTextView);
            checkBoxCompleted = itemView.findViewById(R.id.checkBoxCompleted);
        }
    }
}

