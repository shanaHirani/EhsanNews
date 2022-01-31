package com.jetbrains.handson.mpp.ehsan.data.remote

import com.jetbrains.handson.mpp.ehsan.APIs.API
import com.jetbrains.handson.mpp.ehsan.APIs.NewsApiServices
import com.jetbrains.handson.mpp.ehsan.APIs.WeatherApiServices
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    val newsApiServices: NewsApiServices,
    val weatherApiServices: WeatherApiServices,
    val api: API
) {
    suspend fun getNews(startDate: String) =
        newsApiServices.getNews(apiKey = api.newsApiKey, startDate = startDate)

    suspend fun getWeatherInfo(cityName:String) =
        weatherApiServices.getWeatherInfo(apiKey = api.weatherApiKey,cityName = cityName)
}