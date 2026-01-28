package com.yearprogress.services

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.yearprogress.YearProgressApp
import com.yearprogress.data.YearProgressDatabase
import com.yearprogress.data.dao.DayProgressDao
import java.time.LocalDate

class DailyUpdateWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    
    private lateinit var database: YearProgressDatabase
    private lateinit var dao: DayProgressDao
    
    override fun doWork(): Result {
        return try {
            // Initialize database
            database = YearProgressDatabase.getInstance(applicationContext)
            dao = database.dayProgressDao()
            
            val currentDayOfYear = LocalDate.now().dayOfYear
            
            // Mark yesterday as passed (if not already)
            val yesterdayDay = if (currentDayOfYear > 1) currentDayOfYear - 1 else 365
            val yesterday = dao.getDay(yesterdayDay)
            
            if (yesterday != null && yesterday.status != DayStatus.PAST) {
                Log.d("DailyUpdateWorker", "Marking day $yesterdayDay as passed")
                dao.updateStatus(yesterdayDay, DayStatus.PAST)
            }
            
            // Mark today as TODAY
            val today = dao.getDay(currentDayOfYear)
            if (today != null && today.status != DayStatus.TODAY) {
                Log.d("DailyUpdateWorker", "Marking day $currentDayOfYear as today")
                dao.updateStatus(currentDayOfYear, DayStatus.TODAY)
            }
            
            Result.success()
        } catch (e: Exception) {
            Log.e("DailyUpdateWorker", "Failed to update progress: ${e.message}", e)
            Result.failure()
        }
    }
}
