package com.yearprogress.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.yearprogress.data.YearProgressViewModel
import java.time.LocalDate
import java.time.Year

@Composable
fun CalendarScreen(
    modifier: Modifier = Modifier,
    viewModel: YearProgressViewModel = remember { YearProgressViewModel() }
) {
    val year = Year.now().value
    val daysInYear = if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) 366 else 365
    val daysPassed = viewModel.daysPassed.collectAsState(initial = 0)
    val dayProgress = viewModel.dayProgress.collectAsState(initial = emptyList())
    val currentDayOfYear = LocalDate.now().dayOfYear

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header
            Text(
                text = "Year Progress",
                style = MaterialTheme.typography.headlineMedium
            )
            
            // Progress stats
            Text(
                text = "$daysPassed / $daysInYear days passed",
                style = MaterialTheme.typography.bodyLarge
            )
            
            // Calendar grid
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 60.dp),
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(daysInYear) { day ->
                    DayCircle(
                        day = day + 1,
                        isCurrentDay = (day + 1) == currentDayOfYear,
                        isPassed = (day + 1) <= currentDayOfYear,
                        onClick = { viewModel.toggleDay(day + 1) }
                    )
                }
            }
            
            // Bottom stats
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = "${String.format("%.1f", (daysPassed.toFloat() / daysInYear) * 100)}% completed",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun DayCircle(
    day: Int,
    isCurrentDay: Boolean,
    isPassed: Boolean,
    onClick: () -> Unit
) {
    val color = when {
        isCurrentDay -> Color.Yellow
        isPassed -> Color.White
        else -> Color.Gray.copy(alpha = 0.3f)
    }
    
    Box(
        modifier = Modifier
            .size(50.dp)
            .background(
                color = color,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day.toString(),
            color = if (isPassed || isCurrentDay) Color.Black else Color.Black.copy(alpha = 0.3f)
        )
    }
}
