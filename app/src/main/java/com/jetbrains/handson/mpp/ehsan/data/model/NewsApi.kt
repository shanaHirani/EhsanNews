package com.jetbrains.handson.mpp.ehsan.data.model

import android.os.Parcelable
import android.util.Log
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class AllNews(
    @Json(name = "articles") val newsList: List<NewsApi>,
    val status: String,
    val totalResults: Int,
)

@Parcelize
data class News(
    val isValid: Boolean = true,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: LocalDate?,
    val title: String,
    val url: String?,
    val urlToImage: String?,
) : Parcelable


data class NewsApi(
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

fun NewsApi.asDomainModel(): News {
    var isValidData: Boolean = true

    var publishedDate = LocalDate.now()
    val dateRawFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
    try {
        publishedDate = LocalDate.parse(this.publishedAt, dateRawFormat)
    } catch (e: Exception) {
        isValidData = false
    }

    return News(
        isValid = isValidData,
        author = this.author,
        content = this.content,
        description = this.description,
        publishedAt = publishedDate,
        title = this.title,
        url = this.url,
        urlToImage = this.urlToImage
    )
}

fun List<NewsApi>.asDomainModel(): List<News> {
    return map() {
        it.asDomainModel()
    }.filter { it.isValid }
}