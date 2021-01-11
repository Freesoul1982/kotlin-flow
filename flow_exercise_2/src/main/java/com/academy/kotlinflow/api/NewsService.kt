package com.academy.kotlinflow.api

import com.academy.kotlinflow.data.NewsResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface NewsService {

    @GET("newstories.json?print=pretty")
    suspend fun getNewStories(): LongArray

    @GET("topstories.json?print=pretty")
    suspend fun getTopStories(): LongArray

    @GET("item/{id}.json?print=pretty")
    suspend fun getNews(@Path("id") id: Long): NewsResponse


    companion object {
        private const val BASE_URL = "https://hacker-news.firebaseio.com/v0/"

        fun create(): NewsService {
            val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NewsService::class.java)
        }
    }
}