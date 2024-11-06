package com.example.habittracker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HabitAdapter extends RecyclerView.Adapter<HabitAdapter.HabitViewHolder> {

    private List<Habit> habitList;
    private Context context;

    public HabitAdapter(List<Habit> habitList, Context context) {
        this.habitList = habitList;
        this.context = context;
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
    }

    @Override
    public int getItemCount() {
        return habitList.size();
    }

    public static class HabitViewHolder extends RecyclerView.ViewHolder {
        TextView habitNameTextView, habitDescriptionTextView;

        public HabitViewHolder(@NonNull View itemView) {
            super(itemView);
            habitNameTextView = itemView.findViewById(R.id.habitNameTextView);
            habitDescriptionTextView = itemView.findViewById(R.id.habitDescriptionTextView);
        }
    }

    public void scheduleNotification(String habitName, long triggerTime) {
        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.putExtra("habitName", habitName);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
            Toast.makeText(context, "Notificación programada", Toast.LENGTH_SHORT).show();
        }
    }

}


