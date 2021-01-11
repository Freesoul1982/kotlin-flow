package com.academy.kotlinflow.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.academy.kotlinflow.api.NewsService
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.isActive
import kotlin.math.min

class NewsRepository(context: Context) {
    private val scoresDao: ScoresDao = AppDatabase.getInstance(context).scoresDao()
    private val newsService: NewsService = NewsService.create()
    private val fetchingStatus = MutableLiveData<FetchingStatus>()
    private var scoreValue = 0
    private val scope = MainScope()

    init {
        scope.launch {
            scoresDao.getScores().collect { scores ->
                if(scores.isNotEmpty()) {
                    scoreValue = scores[0].score
                }
            }
        }
    }


    fun getFetchingStatus(): LiveData<FetchingStatus> = fetchingStatus

    fun getNewsData(): Flow<ArrayList<News>> = flow {
        while(currentCoroutineContext().isActive) {
            val news = fetchNews()
            if (news?.isNotEmpty() == true) {
                emit(news)
            } else {
                throw NewsException("No news")
            }
            delay(10000)
        }
    }

    private suspend fun fetchNews(): ArrayList<News>? {
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
            fetchingStatus.postValue(FetchingStatus.FETCHING_SUCCEED)
            return news
        }
        catch (e: Exception) {
            e.printStackTrace()
            fetchingStatus.postValue(FetchingStatus.FETCHING_FAILED)
            return null
        }
    }

    fun cancelScope() {
        scope.cancel()
    }
}