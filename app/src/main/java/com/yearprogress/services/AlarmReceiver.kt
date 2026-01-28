package com.yearprogress.services

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        Log.d("AlarmReceiver", "Day update alarm received")
        
        // Start WorkManager to mark current day as passed
        val workRequest = OneTimeWorkRequestBuilder<DailyUpdateWorker>()
            .build()
        
        WorkManager.getInstance(context).enqueue(workRequest)
    }
}

fun scheduleDailyAlarm(context: Context, hour: Int = 0, minute: Int = 0) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    
    val intent = Intent(context, AlarmReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        0,
        intent,
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )
    
    // Schedule for next midnight
    val calendar = java.util.Calendar.getInstance()
    calendar.timeInMillis = System.currentTimeMillis()
    calendar.set(java.util.Calendar.HOUR_OF_DAY, hour)
    calendar.set(java.util.Calendar.MINUTE, minute)
    calendar.set(java.util.Calendar.SECOND, 0)
    calendar.add(java.util.Calendar.DAY_OF_MONTH, 1)
    
    alarmManager.setExactAndAllowWhileIdle(
        AlarmManager.RTC_WAKEUP,
        calendar.timeInMillis,
        pendingIntent,
        AlarmManager.INTERVAL_DAY
    )
    
    Log.d("AlarmReceiver", "Daily alarm scheduled for ${hour}:$minute")
}
