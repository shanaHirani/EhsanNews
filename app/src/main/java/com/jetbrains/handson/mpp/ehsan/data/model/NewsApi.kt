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

fun List<NewsApi>.asDomainModel(): List<News> {

    // if we con not parse the date defult value is now
    var publishedDate = LocalDate.now()
    val dateRawFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
    return map() {
        try {
            publishedDate = LocalDate.parse(it.publishedAt, dateRawFormat)
        } catch (e: Exception) {
            Log.i("NewsAPI", "con not parse the date of ${it.title}")
        }
        News(
            author = it.author,
            content = it.content,
            description = it.description,
            publishedAt = publishedDate,
            title = it.title,
            url = it.url,
            urlToImage = it.urlToImage
        )
    }
}