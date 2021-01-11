package com.academy.kotlinflow.data

import com.google.gson.annotations.SerializedName

data class NewsResponse (
    @SerializedName("id")
    val id: Long,
    @SerializedName("by")
    val author: String,
    @SerializedName("time")
    val time: Long,
    @SerializedName("title")
    val title: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("score")
    val score: Int
)