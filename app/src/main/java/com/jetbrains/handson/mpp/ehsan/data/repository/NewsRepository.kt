package com.jetbrains.handson.mpp.ehsan.data.repository


import com.jetbrains.handson.mpp.ehsan.R
import com.jetbrains.handson.mpp.ehsan.data.model.News
import com.jetbrains.handson.mpp.ehsan.data.model.WeatherInfo
import com.jetbrains.handson.mpp.ehsan.data.model.asDomainModel
import com.jetbrains.handson.mpp.ehsan.data.model.toDomain
import com.jetbrains.handson.mpp.ehsan.data.remote.RemoteDataSource
import com.jetbrains.handson.mpp.ehsan.netFormat
import com.jetbrains.handson.mpp.ehsan.shared.NetworkResponse
import java.time.LocalDate
import java.time.Period
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val stringResProvider: StringResProvider,
    private val timeProvider: TimeProvider,
) : BaseRepository() {
    suspend fun getNews(): NetworkResponse<List<News>> =
        safeApiCall(
            { remoteDataSource.getNews(getTwoMonthAgo()) },
            { it.newsList.asDomainModel().sortedByDescending { news -> news.publishedAt } }
        )

    suspend fun getWeatherInfo(): NetworkResponse<WeatherInfo> =
        safeApiCall(
            { remoteDataSource.getWeatherInfo(stringResProvider.getStringFromSource(R.string.Sydney)) },
            { it.toDomain() }
        )

    private fun getTwoMonthAgo(): LocalDate {
        return timeProvider.getNowDate()
            .minus(Period.of(0, 2, 0))
    }
}
