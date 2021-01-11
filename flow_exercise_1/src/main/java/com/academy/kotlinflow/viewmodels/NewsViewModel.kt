package com.academy.kotlinflow.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.academy.kotlinflow.data.FetchingStatus
import com.academy.kotlinflow.data.NewsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class NewsViewModel(private val newsRepository: NewsRepository): ViewModel() {

    fun getNews() = newsRepository.getNewsData()

    fun getScores() = newsRepository.getScore()


    fun fetchNews() {
        viewModelScope.launch {
            newsRepository.fetchNews()
        }
    }

    fun getFetchingStatus(): LiveData<Boolean> = Transformations.map(newsRepository.getFetchingStatus()) {
        when(it) {
            FetchingStatus.FETCHING_IN_PROCESS -> true
            FetchingStatus.FETCHING_FAILED, FetchingStatus.FETCHING_SUCCEED, FetchingStatus.FETCHING_INITIAL -> false
        }
    }

    fun setScore(score: Int) {
        newsRepository.setScore(score)
    }
}