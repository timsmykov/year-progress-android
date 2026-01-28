package com.yearprogress.ui.widgets

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceModifyingGlanceAppWidgetReceiver
import com.yearprogress.R

class YearProgressWidgetReceiver : GlanceModifyingGlanceAppWidgetReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
    }
}

@GlanceAppWidget
class YearProgressWidget : GlanceModifyingGlanceAppWidgetReceiver() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        // TODO: Implement simple widget showing progress
        // For MVP, just show "Year Progress" with percentage
        provideContent {
            Text(
                text = "Year Progress",
                style = TextStyle(fontSize = 16.sp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${getCurrentProgress(context)}% completed",
                style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold)
            )
        }
    }
    
    private fun getCurrentProgress(context: Context): Float {
        // Calculate progress based on day of year
        val dayOfYear = java.time.LocalDate.now().dayOfYear
        val totalDays = if (java.time.Year.now().isLeap) 366 else 365
        return (dayOfYear.toFloat() / totalDays) * 100
    }
}
