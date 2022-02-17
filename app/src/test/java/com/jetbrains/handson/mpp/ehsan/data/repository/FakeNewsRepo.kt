package com.jetbrains.handson.mpp.ehsan.data.repository

import com.jetbrains.handson.mpp.ehsan.data.model.News
import com.jetbrains.handson.mpp.ehsan.data.model.WeatherInfo
import com.jetbrains.handson.mpp.ehsan.shared.NetworkResponse
import java.time.LocalDate

class FakeNewsRepo : NewsRepositoryInterface {

    lateinit var weatherInfo: NetworkResponse<WeatherInfo>
    lateinit var news:NetworkResponse<List<News>>

    fun setup(getWeatherInfoSuccess: Boolean, getNewsSuccess: Boolean) {
        if (getWeatherInfoSuccess) {
            weatherInfo = NetworkResponse.Success(
                WeatherInfo(5.0, 30.0, 32.0, "cloud")
            )
        } else {
            weatherInfo = NetworkResponse.Failure(
                false, 500, null
            )
        }
        if (getNewsSuccess) {
            news =NetworkResponse.Success(listOf(news1, news2))
        } else {
            news = NetworkResponse.Failure(
                false, 500, null
            )
        }

    }

    var news1 = News(
        "shaghayegh",
        "news about art",
        "important news",
        LocalDate.of(2020, 1, 1),
        "art",
        "google.com",
        "google"
    )
    var news2 = News(
        "ehsan",
        "news about math",
        "math history",
        LocalDate.of(2020, 1, 1),
        "math",
        "google.com",
        "google"
    )


    override suspend fun getWeatherInfo(): NetworkResponse<WeatherInfo> {
        return weatherInfo
    }

    override suspend fun getNews(): NetworkResponse<List<News>> {
        return news
    }
}