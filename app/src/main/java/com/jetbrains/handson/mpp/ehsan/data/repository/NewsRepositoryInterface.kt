package com.jetbrains.handson.mpp.ehsan.data.repository

import com.jetbrains.handson.mpp.ehsan.data.model.News
import com.jetbrains.handson.mpp.ehsan.data.model.WeatherInfo
import com.jetbrains.handson.mpp.ehsan.shared.NetworkResponse

interface NewsRepositoryInterface {
    suspend fun getWeatherInfo(): NetworkResponse<WeatherInfo>
    suspend fun getNews(): NetworkResponse<List<News>>
}