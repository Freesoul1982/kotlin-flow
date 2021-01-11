package com.academy.kotlinflow.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.academy.kotlinflow.api.NewsService
import com.google.gson.Gson
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlin.math.min

class NewsRepository(context: Context) {
    private val scoresDao: ScoresDao = AppDatabase.getInstance(context).scoresDao()
    private val newsService: NewsService = NewsService.create()
    private val fetchingStatus = MutableLiveData<FetchingStatus>()
    private val newsData = MutableLiveData<ArrayList<News>>()
    private var scoreValue = 0



    fun getFetchingStatus(): LiveData<FetchingStatus> = fetchingStatus

    fun getNewsData(): LiveData<ArrayList<News>> = newsData

    fun getScore() = scoresDao.getScores()

    suspend fun fetchNews() {
        while(currentCoroutineContext().isActive) {
            try {
                fetchingStatus.postValue(FetchingStatus.FETCHING_IN_PROCESS)
                val result = newsService.getTopStories()
                val maxRange = min(result.size, 10)
                val news = ArrayList<News>()
                for(i in 0 until maxRange) {
                    val id = result[i]
                    val newsResponse = newsService.getNews(id)
                    if(newsResponse.score >= scoreValue) {
                        news.add(News(newsResponse.id, newsResponse.author, newsResponse.title, newsResponse.url, newsResponse.time, newsResponse.score))
                    }
                }
                Log.i("NewsRepository", "News size: ${news.size}")
                val random = (0..200).random()
                scoresDao.insert(Score(random))
                newsData.postValue(news)
                fetchingStatus.postValue(FetchingStatus.FETCHING_SUCCEED)
            }
            catch (e: Exception) {
                e.printStackTrace()
                fetchingStatus.postValue(FetchingStatus.FETCHING_FAILED)
            }
            delay(10000)
        }
    }


    fun setScore(score: Int) {
        Log.i("NewsRepository", "Score: $score")
        scoreValue = score
    }
}