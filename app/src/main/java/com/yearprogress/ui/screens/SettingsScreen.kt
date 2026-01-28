package com.yearprogress.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.yearprogress.YearProgressViewModel
import java.time.LocalDate

@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: YearProgressViewModel = remember { YearProgressViewModel() }
) {
    var startDay by remember { 1 }
    var customYear by remember { LocalDate.now().year }
    
    val isLeapYear = (customYear % 4 == 0 && (customYear % 100 != 0 || customYear % 400 == 0))
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineMedium
        )
        
        Spacer(modifier = Modifier.size(24.dp))
        
        // Year selection
        OutlinedTextField(
            value = customYear.toString(),
            onValueChange = { customYear = it.toIntOrNull() ?: customYear },
            label = { Text("Year") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.size(16.dp))
        
        // Start day selection
        OutlinedTextField(
            value = startDay.toString(),
            onValueChange = { startDay = it.toIntOrNull() ?: 1 },
            label = { Text("Start Day (1-365)") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.size(24.dp))
        
        // Info
        Text(
            text = "This year is ${if (isLeapYear) "leap" else "common"} (${if (isLeapYear) "366" else "365"} days)",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )
        
        Spacer(modifier = Modifier.weight(1f))
        
        // Action buttons
        Button(
            onClick = {
                viewModel.resetYear()
                // TODO: Implement custom year start day
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Reset Year")
        }
    }
}
