package com.jetbrains.handson.mpp.ehsan.APIs

import com.jetbrains.handson.mpp.ehsan.data.model.WeatherInfoRemoteDataSource
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiServices {
    @GET("data/2.5/weather?")
    fun getWeatherInfo(
        @Query("q") cityName: String,
        @Query("APPID") apiKey: String,
    ): Call<WeatherInfoRemoteDataSource>
}
