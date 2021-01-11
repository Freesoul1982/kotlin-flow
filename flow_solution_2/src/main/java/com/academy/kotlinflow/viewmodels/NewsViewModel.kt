package com.academy.kotlinflow.viewmodels

import androidx.lifecycle.*
import com.academy.kotlinflow.data.FetchingStatus
import com.academy.kotlinflow.data.News
import com.academy.kotlinflow.data.NewsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class NewsViewModel(private val newsRepository: NewsRepository): ViewModel() {
    private val newsData = MutableLiveData<ArrayList<News>>()
    private val isFetching = MutableLiveData<Boolean>()

    fun getNews() = newsData

    fun isFetching() = isFetching

    init {
        viewModelScope.launch {
            newsRepository.getFetchingStatus().collect {
                when(it) {
                    FetchingStatus.FETCHING_IN_PROCESS -> isFetching.postValue(true)
                    FetchingStatus.FETCHING_FAILED, FetchingStatus.FETCHING_SUCCEED, FetchingStatus.FETCHING_INITIAL -> isFetching.postValue(false)
                }
            }
        }
    }

    fun fetchNews() {
        viewModelScope.launch {
            newsRepository.getNewsData()
                .catch {
                    emit(ArrayList())
                }
                .collect {
                    newsData.postValue(it)
                }
        }
    }


    override fun onCleared() {
        super.onCleared()
        newsRepository.cancelScope()
    }
}