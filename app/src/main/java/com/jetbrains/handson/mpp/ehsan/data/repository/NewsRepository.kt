package com.jetbrains.handson.mpp.ehsan.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.jetbrains.handson.mpp.ehsan.R
import com.jetbrains.handson.mpp.ehsan.data.model.*
import com.jetbrains.handson.mpp.ehsan.data.remote.RemoteDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val stringResProvider: StringResProvider
) {
    fun getNews(
        failureJob: (errorExplanation: String) -> Unit,
        successJob: (response: List<News>?) -> Unit
    ) {
        remoteDataSource.getNews(getTwoDayAgo()).enqueue(object : Callback<AllNews> {

            override fun onFailure(call: Call<AllNews>, t: Throwable) {
                failureJob(t.message.toString())
            }

            override fun onResponse(call: Call<AllNews>, response: Response<AllNews>) {
                if (response.code() == 200) {
                    successJob(response.body()?.newsList?.asDomainModel()?.sortedByDescending { news -> news.publishedAt })
                } else {
                    failureJob(response.errorBody().toString())
                }
            }
        })
    }

    fun getWeatherInfo(
        failureJob: (errorExplanation: String) -> Unit,
        successJob: (response: WeatherInfo?) -> Unit
    ) {
        remoteDataSource.getWeatherInfo(stringResProvider.getStringFromSource(R.string.Sydney))
            .enqueue(object : Callback<WeatherInfoRemoteDataSourceApi> {

                override fun onFailure(call: Call<WeatherInfoRemoteDataSourceApi>, t: Throwable) {
                    failureJob(t.message.toString())
                }

                override fun onResponse(
                    call: Call<WeatherInfoRemoteDataSourceApi>,
                    response: Response<WeatherInfoRemoteDataSourceApi>
                ) {
                    if (response.code() == 200) {
                        successJob(response.body()?.toDomain())
                    } else {
                        failureJob(response.errorBody().toString())
                    }

                }
            })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getTwoDayAgo(): String {
        return LocalDate.now()
            .minus(Period.of(0, 0, 20))
            .format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
    }
}
