package com.jetbrains.handson.mpp.ehsan.data.model

import android.os.Parcelable
import android.util.Log
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class AllNewsRemoteDataSourceApi(
    @Json(name = "articles") val newsList: List<NewsRemoteDataSourceApi>,
    val status: String,
    val totalResults: Int,
)

@Parcelize
data class News(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: LocalDate?,
    val title: String,
    val url: String?,
    val urlToImage: String?,
) : Parcelable


data class NewsRemoteDataSourceApi(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String,
    val url: String?,
    val urlToImage: String?,
)

data class Source(
    val id: String?,
    val name: String?,
)

fun NewsRemoteDataSourceApi.asDomainModel(): News?{

    var publishedDate: LocalDate
    val dateRawFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
    try {
        publishedDate = LocalDate.parse(this.publishedAt, dateRawFormat)
    } catch (e: Exception) {
        return null
    }

    return News(
        author = this.author,
        content = this.content,
        description = this.description,
        publishedAt = publishedDate,
        title = this.title,
        url = this.url,
        urlToImage = this.urlToImage
    )
}

fun List<NewsRemoteDataSourceApi>.asDomainModel(): List<News> {
    return mapNotNull {
        it.asDomainModel()
    }
}