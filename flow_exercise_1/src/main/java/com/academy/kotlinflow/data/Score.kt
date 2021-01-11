package com.academy.kotlinflow.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scores")
data class Score (
    val score: Int) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}