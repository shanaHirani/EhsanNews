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
class HomePageViewModel @Inject constructor(private val newsRepository: NewsRepository) : ViewModel() {

    private val _newsList = MutableLiveData<NetworkResponse<List<News>>>()
    val newsList: LiveData<NetworkResponse<List<News>>>
        get() = _newsList

    private val _weatherInfo = MutableLiveData<WeatherInfo>()
    val weatherInfo: LiveData<WeatherInfo>
        get() = _weatherInfo

    private val _errorMessage = MutableLiveData<Event<String>>()
    val errorMessage: LiveData<Event<String>>
        get() = _errorMessage

    private val _navigateToSelectedNews = MutableLiveData<Event<News>>()
    val navigateToSelectedNews:LiveData<Event<News>>
        get() = _navigateToSelectedNews

    init {
        _newsList.value = NetworkResponse.Start
        getNewsList()
        getWeatherInfo()

    }

    private fun getWeatherInfo()= viewModelScope.launch {

        val result = newsRepository.getWeatherInfo()
        if (result is NetworkResponse.Failure) {
           // _errorMessage.value = Event((result.value as NetworkResponse.Failure).errorCode.toString())
        }
        if (result is NetworkResponse.Success) {
            _weatherInfo.value = result.value
        }

    }
    private fun getNewsList() = viewModelScope.launch {
        _newsList.value = NetworkResponse.Loading
        _newsList.value = newsRepository.getNews()
        if (_newsList.value is NetworkResponse.Failure) {
                _errorMessage.value = Event((_newsList.value as NetworkResponse.Failure).errorCode.toString())
        }
    }

    fun displaySelectedNews(news:News){
        _navigateToSelectedNews.value = Event(news)
    }
}