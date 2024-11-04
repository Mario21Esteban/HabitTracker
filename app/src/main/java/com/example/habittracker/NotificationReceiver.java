package com.example.habittracker;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String habitName = intent.getStringExtra("habitName");

        // Crear la notificación
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "habitReminder")
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Recordatorio de Hábito")
                .setContentText("Es hora de completar el hábito: " + habitName)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1001, builder.build());
    }
}

