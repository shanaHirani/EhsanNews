package com.jetbrains.handson.mpp.ehsan.data.model

import com.squareup.moshi.Json

data class WeatherInfoRemoteDataSource(
    val base: String?,
    val clouds: Clouds?,
    val cod: Int?,
    val coord: Coord?,
    val dt: Int?,
    val id: Int?,
    val main: Main?,
    val name: String?,
    val sys: Sys?,
    val timezone: Int?,
    val visibility: Int?,
    val weather: List<Weather>?,
    val wind: Wind?
)

data class Clouds(
    val all: Int?
)

data class Coord(
    val lat: Double?,
    val lon: Double?
)

data class Main(
    val feels_like: Double?,
    val humidity: Int?,
    val pressure: Int?,
    val temp: Double?,
    val temp_max: Double?,
    val temp_min: Double?
)

data class Sys(
    val country: String?,
    val id: Int?,
    val sunrise: Int?,
    val sunset: Int?,
    val type: Int?
)

data class Weather(
    val description: String?,
    val icon: String?,
    val id: Int?,
    @Json(name = "main") val weatherStatus: String?,
)

data class Wind(
    val deg: Int?,
    val gust: Double?,
    val speed: Double?
)

data class WeatherInfoDomain(
    val minTmp: Double?,
    val maxTmp: Double?,
    val filesLikeTmp: Double?,
    val weatherStatus: String?
)

fun WeatherInfoRemoteDataSource.toDomain(): WeatherInfoDomain {
    return WeatherInfoDomain(
        minTmp = this.main?.temp_min.kelvinToCelsius(),
        maxTmp = this.main?.temp_max.kelvinToCelsius(),
        filesLikeTmp = this.main?.feels_like.kelvinToCelsius(),
        weatherStatus = this.weather?.get(0)?.weatherStatus
    )
}

private fun Double?.kelvinToCelsius(): Double? {
    return this?.minus(273.15)
}


