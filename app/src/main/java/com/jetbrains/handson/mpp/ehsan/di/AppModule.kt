package com.jetbrains.handson.mpp.ehsan.di

import com.jetbrains.handson.mpp.ehsan.APIs.API
import com.jetbrains.handson.mpp.ehsan.APIs.NewsApiServices
import com.jetbrains.handson.mpp.ehsan.APIs.WeatherApiServices
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideNewsApiService(api:API):NewsApiServices{
        return api.newsRetrofitObject.create(NewsApiServices::class.java)
    }

    @Singleton
    @Provides
    fun provideWeatherInfoApiService(api:API):WeatherApiServices{
        return api.weatherRetrofitObject.create(WeatherApiServices::class.java)
    }

}