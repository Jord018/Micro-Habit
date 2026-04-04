package com.lingth.Micro_habit.database

import android.R.attr.data
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    val name: String,
    val email: String,
    val goalCategory: String,
    val energyLevel: Int,
    @ColumnInfo(name = "created_at") val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "habits")
data class Habit(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val cycle: String, // เช่น "Daily", "Weekly"
    val difficulty: Int, // Base difficulty
    @ColumnInfo(name = "current_difficulty") val currentDifficulty: Int, // AI ปรับให้
    val condition: String, // Trigger เช่น "After coding"
    @ColumnInfo(name = "is_completed") val isCompleted: Boolean,
    @ColumnInfo(name = "ai_optimize") val aiOptimize: Boolean = true,
    @ColumnInfo(name = "last_optimized_at") val lastOptimizedAt: Long = System.currentTimeMillis()
)

@Entity(
    tableName = "daily_logs",
    foreignKeys = [
        ForeignKey(
            entity = Habit::class,
            parentColumns = ["id"],
            childColumns = ["habit_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["habit_id"])] // เพิ่ม Index เพื่อให้ Query เร็วขึ้น
)
data class DailyLog(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "habit_id") val habitId: Int,
    @ColumnInfo(name = "log_date") val logDate: Long, // วันที่บันทึก
    @ColumnInfo(name = "success_level") val successLevel: Int, // 0: Fail, 1: Easy, 2: Standard
    val moodScore: Int,
    val isProactive: Boolean = false,
    val contextSnapshot: String? = null // เก็บ context สั้นๆ เช่น "At CMU" หรือ "Low Battery"
)

@Entity(
    tableName = "ai_insights",
    foreignKeys = [
        ForeignKey(
            entity = Habit::class,
            parentColumns = ["id"],
            childColumns = ["habit_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["habit_id"])]
)
data class AiInsights(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "habit_id") val habitId: Int,
    val insight: String, // เช่น "คุณมักพลาดนิสัยนี้ในวันสอบ"
    val suggestion: String, // เช่น "ลองลดระดับความยากลงในวันที่มีสอบ"
    @ColumnInfo(name = "created_at") val createdAt: Long = System.currentTimeMillis()
)