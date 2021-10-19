package com.jetbrains.handson.mpp.ehsan.ui.homePage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jetbrains.handson.mpp.ehsan.data.model.News
import com.jetbrains.handson.mpp.ehsan.data.model.WeatherInfo
import com.jetbrains.handson.mpp.ehsan.data.repository.NewsRepository
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

    private val _weatherApiStatus = MutableLiveData<ApiStatus>()
    val weatherApiStatus:LiveData<ApiStatus>
        get() = _weatherApiStatus

    private val _toastMassage = MutableLiveData<String>()
    val toastMassage:LiveData<String>
    get() = _toastMassage

    private val _navigateToSelectedNews = MutableLiveData<News>()
    val navigateToSelectedNews:LiveData<News>
        get() = _navigateToSelectedNews

    init {
        _newsApiStatus.value = ApiStatus.LOADING
        newsRepository.getNews({errorExplanation->
            _toastMassage.value = errorExplanation
            _newsApiStatus.value = ApiStatus.ERROR
        }, {response->
            _newsList.value = response
            _newsApiStatus.value = ApiStatus.DONE
        })

        newsRepository.getWeatherInfo({errorExplanation->
            _toastMassage.value = errorExplanation
            _weatherApiStatus.value = ApiStatus.ERROR
        }, {response->
            _weatherInfo.value = response
            _weatherApiStatus.value = ApiStatus.DONE
        })
    }

    fun displaySelectedNews(news:News){
        _navigateToSelectedNews.value = news
    }

    fun displayNewsComplete(){
        _navigateToSelectedNews.value = null
    }
}