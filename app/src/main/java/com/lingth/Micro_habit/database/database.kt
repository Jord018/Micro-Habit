package com.lingth.Micro_habit.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [User::class, Habit::class, HabitProgression::class, DailyLog::class, AiInsights::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    
}
