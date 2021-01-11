package com.academy.kotlinflow.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class News (
    @PrimaryKey val id: Long,
    val author: String,
    val title: String,
    val url: String?,
    val timestamp: Long,
    val score: Int)