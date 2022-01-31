package com.jetbrains.handson.mpp.ehsan.APIs

import com.jetbrains.handson.mpp.ehsan.data.model.AllNews
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiServices {
    @GET("/v2/everything?q=tesla")
    suspend fun getNews(@Query("apikey") apiKey: String,
                @Query("from") startDate: String,
                //@Query("sortBy") sortBy: String = "publishedAt"
    ):AllNews
}
