package com.lingth.Micro_habit.database

import android.R.attr.data
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

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
data class User(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "password") val password: String,
    @ColumnInfo(name = "habit_id") val habitId: Int?
)
@Entity
data class Habit(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "habit_name") val name: String,
    @ColumnInfo(name = "habit_cycle") val cycle: String,
    @ColumnInfo(name = "habit_image") val image: String,
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