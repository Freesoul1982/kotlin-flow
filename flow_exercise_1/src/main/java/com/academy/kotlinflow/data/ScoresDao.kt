package com.academy.kotlinflow.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ScoresDao {
    @Query("SELECT * FROM scores ORDER BY id DESC")
    fun getScores(): LiveData<List<Score>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(score: Score)
}