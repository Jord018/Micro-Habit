package com.lingth.Micro_habit.model

import androidx.compose.ui.graphics.Color

// ข้อมูลนิสัย (Habit)
data class Habit(
    val id: String,
    val title: String,
    val description: String,
    val progress: Float, // 0.0 to 1.0
    val icon: String,
    val color: Color,
    val isCompleted: Boolean = false,
)

// ข้อมูลภาพรวม (Stats)
data class UserStats(
    val flowScore: Int,
    val currentStreak: Int,
    val focusPoints: Int,
)

// ข้อมูลสถานะของหน้า Dashboard
data class DashboardUiState(
    val stats: UserStats = UserStats(0, 0, 0),
    val habits: List<Habit> = emptyList(),
)

