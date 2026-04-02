package com.lingth.Micro_habit.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.lingth.Micro_habit.model.DashboardUiState
import com.lingth.Micro_habit.model.Habit
import com.lingth.Micro_habit.model.UserStats
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DashboardViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        loadDashboardData()
    }

    private fun loadDashboardData() {
        // จำลองการดึงข้อมูลจาก API หรือ Database
        _uiState.update {
            it.copy(
                stats = UserStats(72, 12, 85),
                habits = listOf(
                    Habit(
                        "1",
                        "Morning Hydration",
                        "500ml upon waking up",
                        0.75f,
                        "water_drop",
                        Color(0xFF69DBAD),
                    ),
                    Habit(
                        "2",
                        "Deep Reading",
                        "10 pages of non-fiction",
                        1.0f,
                        "auto_stories",
                        Color(0xFFDDB7FF),
                    )
                )
            )
        }
    }

    fun onHabitComplete(habitId: String) {
        // Logic เมื่อกดปุ่ม Complete
        val updatedHabits = _uiState.value.habits.map {
            if (it.id == habitId) it.copy(isCompleted = true, progress = 1.0f) else it
        }
        _uiState.update { it.copy(habits = updatedHabits) }
    }
}