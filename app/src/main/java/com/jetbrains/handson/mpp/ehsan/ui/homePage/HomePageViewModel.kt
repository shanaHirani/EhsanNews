package com.jetbrains.handson.mpp.ehsan.ui.homePage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jetbrains.handson.mpp.ehsan.data.model.News
import com.jetbrains.handson.mpp.ehsan.data.model.WeatherInfo
import com.jetbrains.handson.mpp.ehsan.data.repository.NewsRepository
import com.jetbrains.handson.mpp.ehsan.shared.Event
import com.jetbrains.handson.mpp.ehsan.shared.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class ApiStatus { LOADING, ERROR, DONE }

@HiltViewModel
class HomePageViewModel @Inject constructor(private val newsRepository: NewsRepository) :
    ViewModel() {

    private val _newsApiStatus = MutableLiveData<ApiStatus>()
    val newsApiStatus: LiveData<ApiStatus>
        get() = _newsApiStatus


    private val _newsList = MutableLiveData<List<News>>()
    val newsList: LiveData<List<News>>
        get() = _newsList

    private val _weatherInfo = MutableLiveData<WeatherInfo>()
    val weatherInfo: LiveData<WeatherInfo>
        get() = _weatherInfo

    private val _apiError = MutableLiveData<Event<NetworkResponse.Failure>>()
    val apiError: LiveData<Event<NetworkResponse.Failure>>
        get() = _apiError

    private val _navigateToSelectedNews = MutableLiveData<Event<News>>()
    val navigateToSelectedNews: LiveData<Event<News>>
        get() = _navigateToSelectedNews

    init {
        getNews()
        getWeatherInf()
    }

    fun getNews() = viewModelScope.launch {
        _newsApiStatus.value = ApiStatus.LOADING
        val result = newsRepository.getNews()
        if (result is NetworkResponse.Success) {
            _newsApiStatus.value = ApiStatus.DONE
            _newsList.value = result.value
        }
        if (result is NetworkResponse.Failure) {
            _newsApiStatus.value = ApiStatus.ERROR
            _apiError.value = Event(result)
        }
    }

    fun getWeatherInf() = viewModelScope.launch {
        val result = newsRepository.getWeatherInfo()
        if (result is NetworkResponse.Failure) {
            _apiError.value = Event(result)
        }
        if (result is NetworkResponse.Success) {
            _weatherInfo.value = result.value
        }

    }

    fun displaySelectedNews(news: News) {
        _navigateToSelectedNews.value = Event(news)
    }
}