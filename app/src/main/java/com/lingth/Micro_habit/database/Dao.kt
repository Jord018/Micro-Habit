package com.lingth.Micro_habit.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getAll(): List<User>

    @Query("SELECT * FROM users WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<User>

    @Insert
    fun insertAll(vararg users: User)

    @Delete
    fun delete(user: User)
}

@Dao
interface HabitDao {
    @Query("SELECT * FROM habits")
    fun getAllHabits(): Flow<List<Habit>>

    @Query("SELECT * FROM habits WHERE is_completed = 0")
    fun getAllActiveHabits(): Flow<List<Habit>>

    @Query("SELECT * FROM habits WHERE is_completed = 1")
    fun getAllCompletedHabits(): Flow<List<Habit>>

    @Query("SELECT * FROM habits WHERE id = :habitId")
    suspend fun getHabitById(habitId: Int): Habit?

    @Insert
    suspend fun insertHabit(habit: Habit)

    @Update
    suspend fun updateHabit(habit: Habit)

    @Delete
    suspend fun deleteHabit(habit: Habit)

    @Query("UPDATE habits SET is_completed = :isCompleted WHERE id = :habitId")
    suspend fun updateCompletionStatus(habitId: Int, isCompleted: Boolean)

    // สำหรับ AI: ดึงนิสัยที่ไม่ได้ Optimize นานแล้ว
    @Query("SELECT * FROM habits WHERE ai_optimize = 1 ORDER BY last_optimized_at ASC LIMIT 1")
    suspend fun getNextHabitToOptimize(): Habit?
}

@Dao
interface DailyLogDao {
    @Insert
    suspend fun insertLog(log: DailyLog)

    @Query("SELECT * FROM daily_logs WHERE habit_id = :habitId ORDER BY log_date DESC")
    fun getLogsForHabit(habitId: Int): Flow<List<DailyLog>>

    // สำหรับคำนวณ Success Rate 7 วันล่าสุด
    @Query("""
        SELECT COUNT(*) FROM daily_logs 
        WHERE habit_id = :habitId 
        AND success_level >= 1 
        AND log_date >= :sevenDaysAgo
    """)
    suspend fun getSuccessCountInLast7Days(habitId: Int, sevenDaysAgo: Long): Int

    // เช็กว่าวันนี้กด Done ไปหรือยัง
    @Query("SELECT COUNT(*) FROM daily_logs WHERE habit_id = :habitId AND log_date >= :startOfDay")
    suspend fun isHabitDoneToday(habitId: Int, startOfDay: Long): Int
}


@Dao
interface AiInsightsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInsight(insight: AiInsights)

    // Update 'createdAt' to 'created_at' if you added @ColumnInfo
    @Query("SELECT * FROM ai_insights WHERE habit_id = :habitId ORDER BY created_at DESC LIMIT 1")
    fun getLatestInsightForHabit(habitId: Int): Flow<AiInsights?>

    @Query("DELETE FROM ai_insights WHERE habit_id = :habitId")
    suspend fun deleteInsightsByHabit(habitId: Int)
}