package com.jetbrains.handson.mpp.ehsan.ui.homePage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jetbrains.handson.mpp.ehsan.data.model.News
import com.jetbrains.handson.mpp.ehsan.data.model.WeatherInfo
import com.jetbrains.handson.mpp.ehsan.data.repository.NewsRepository
import com.jetbrains.handson.mpp.ehsan.shared.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

enum class ApiStatus { LOADING, ERROR, DONE }

@HiltViewModel
class HomePageViewModel @Inject constructor(private val newsRepository: NewsRepository) : ViewModel() {

    private val _newsList = MutableLiveData<List<News>>()
    val newsList: LiveData<List<News>>
        get() = _newsList

    private val _weatherInfo = MutableLiveData<WeatherInfo>()
    val weatherInfo: LiveData<WeatherInfo>
        get() = _weatherInfo

    private val _newsApiStatus = MutableLiveData<ApiStatus>()
    val newsApiStatus: LiveData<ApiStatus>
        get() = _newsApiStatus

    private val _errorMessage = MutableLiveData<Event<String>>()
    val errorMessage: LiveData<Event<String>>
        get() = _errorMessage

    private val _navigateToSelectedNews = MutableLiveData<Event<News>>()
    val navigateToSelectedNews:LiveData<Event<News>>
        get() = _navigateToSelectedNews

    init {
        _newsApiStatus.value = ApiStatus.LOADING
        newsRepository.getNews({errorExplanation->
            _errorMessage.value = Event(errorExplanation)
            _newsApiStatus.value = ApiStatus.ERROR
        }, {response->
            _newsList.value = response
            _newsApiStatus.value = ApiStatus.DONE
        })

        newsRepository.getWeatherInfo({errorExplanation->
            _errorMessage.value = Event(errorExplanation)
        }, {response->
            _weatherInfo.value = response
        })
    }

    fun displaySelectedNews(news:News){
        _navigateToSelectedNews.value = Event(news)
    }
}