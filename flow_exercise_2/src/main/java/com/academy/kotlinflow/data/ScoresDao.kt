package com.academy.kotlinflow.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ScoresDao {
    @Query("SELECT * FROM scores ORDER BY id DESC")
    fun getScores(): Flow<List<Score>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(score: Score)
}