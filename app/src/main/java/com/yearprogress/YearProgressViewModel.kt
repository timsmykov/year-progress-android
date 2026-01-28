package com.yearprogress

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class YearProgressViewModel : ViewModel() {
    
    private val _daysPassed = MutableStateFlow(0)
    val daysPassed: StateFlow<Int> = _daysPassed.asStateFlow()
    
    private val _dayProgress = MutableStateFlow<List<Int>>(emptyList())
    val dayProgress: StateFlow<List<Int>> = _dayProgress.asStateFlow()
    
    init {
        calculateProgress()
    }
    
    private fun calculateProgress() {
        val currentDayOfYear = LocalDate.now().dayOfYear
        _daysPassed.value = currentDayOfYear
        
        // Generate progress list (1 = passed, 0 = not passed yet)
        val progress = (1..currentDayOfYear).toList()
        _dayProgress.value = progress
    }
    
    fun toggleDay(dayOfYear: Int) {
        viewModelScope.launch {
            val currentProgress = _dayProgress.value.toMutableList()
            val index = dayOfYear - 1
            
            if (index in currentProgress.indices) {
                currentProgress[index] = if (currentProgress[index] == 1) 0 else 1
                _dayProgress.value = currentProgress
            }
        }
    }
    
    fun resetYear() {
        _daysPassed.value = 0
        _dayProgress.value = emptyList()
        calculateProgress()
    }
}
