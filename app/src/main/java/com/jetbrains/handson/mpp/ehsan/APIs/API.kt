package com.jetbrains.handson.mpp.ehsan.APIs

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class API @Inject constructor() {
    //why http cause problem in higher Api
    //Http could cause security problem in higher Api it need to be manually mention in manifests it is ok to use http
    //android:usesCleartextTraffic="true"
    //Https
    val newsBaseURL ="https://newsapi.org/"
    val newsApiKey = "d66f6139d662401c8cf9665b61a9eabd"

    val weatherBaseUrl = "http://api.openweathermap.org/"
    val weatherApiKey = "e09fedce0b7671e2871c01d9b0fdfde2"

    val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    val newsRetrofitObject = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(newsBaseURL)
    .build()

    val weatherRetrofitObject = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(weatherBaseUrl)
        .build()
}