package com.jetbrains.handson.mpp.ehsan.data.remote

import android.util.Log
import com.jetbrains.handson.mpp.ehsan.APIs.API
import com.jetbrains.handson.mpp.ehsan.APIs.NewsApiServices
import com.jetbrains.handson.mpp.ehsan.APIs.WeatherApiServices
import com.jetbrains.handson.mpp.ehsan.data.model.AllNewsRemoteDataSourceApi
import com.jetbrains.handson.mpp.ehsan.netFormat
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    private val newsApiServices: NewsApiServices,
    private val weatherApiServices: WeatherApiServices,
    val api: API
) {
    suspend fun getNews(startDate:LocalDate):AllNewsRemoteDataSourceApi {
        return newsApiServices.getNews(apiKey = api.newsApiKey, startDate = startDate.netFormat())
    }

    suspend fun getWeatherInfo(cityName:String) =
        weatherApiServices.getWeatherInfo(apiKey = api.weatherApiKey,cityName = cityName)
}