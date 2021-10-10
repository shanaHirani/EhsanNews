package com.jetbrains.handson.mpp.ehsan.data.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

data class AllNews(
        @Json(name = "articles")val newsList: List<News>,
        val status: String,
        val totalResults: Int
)

@Parcelize
data class News(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String,
    val url: String?,
    val urlToImage: String?
):Parcelable

@Parcelize
data class Source(
    val id: String?,
    val name: String?
):Parcelable