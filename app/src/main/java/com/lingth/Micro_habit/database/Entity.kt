package com.lingth.Micro_habit.database

import android.R.attr.data
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


data class User(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "password") val password: String,
    @ColumnInfo(name = "goal_category") val goalCategory: String,
    @ColumnInfo(name = "energy_level") val energyLevel: Int,
    @ColumnInfo(name = "created_at") val createdAt: Long = System.currentTimeMillis()
)
@Entity
data class Habit(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "habit_name") val name: String,
    @ColumnInfo(name = "habit_cycle") val cycle: String,
    @ColumnInfo(name = "habit_image") val image: String,
    @ColumnInfo(name = "base_difficulty") val difficulty: Int,
    @ColumnInfo(name = "trigger_condition") val condition: String,
    @ColumnInfo(name = "ai_optimize") val aiOptimize: Boolean = false,
    @ColumnInfo(name = "is_completed") val isCompleted: Boolean = false,
    @ColumnInfo(name = "created_at") val createdAt: Long = System.currentTimeMillis()
)
@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Habit::class,
            parentColumns = ["id"],
            childColumns = ["habit_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class HabitProgression(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "habit_id") val habitId: Int,
    @ColumnInfo(name = "progression") val progression: Int
)

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Habit::class,
            parentColumns = ["id"],
            childColumns = ["habit_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class DailyLog(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "habit_id") val habitId: Int,
    @ColumnInfo(name = "log_date") val logDate: Long,
    @ColumnInfo(name = "success_level") val logValue: Int,
    @ColumnInfo(name = "mood_score") val moodScore: Int,
    @ColumnInfo(name = "is_proactive") val isProactive: Boolean = false,
    @ColumnInfo(name = "created_at") val createdAt: Long = System.currentTimeMillis()
)

data class  AiInsights(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "habit_id") val habitId: Int,
    @ColumnInfo(name = "insight") val insight: String,
    @ColumnInfo(name = "suggestion") val suggestion: String
)