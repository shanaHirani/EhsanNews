package com.jetbrains.handson.mpp.ehsan.data.repository


import com.jetbrains.handson.mpp.ehsan.data.model.AllNews
import com.jetbrains.handson.mpp.ehsan.shared.NetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Singleton

abstract class BaseRepository(

) {

    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): NetworkResponse<T> {
        return withContext(Dispatchers.IO) {
            try {
                NetworkResponse.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        NetworkResponse.Failure(false, throwable.code(), throwable.response()?.errorBody())
                    }
                    else -> {
                        NetworkResponse.Failure(true, null, null)
                    }
                }

            }
        }
    }

    suspend fun <T,S> safeApiCall(
        apiCall: suspend () -> T,
        transform: (T)->S
    ): NetworkResponse<S> {
        return withContext(Dispatchers.IO) {
            try {
                NetworkResponse.Success(transform(apiCall.invoke()))
            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        NetworkResponse.Failure(false, throwable.code(), throwable.response()?.errorBody())
                    }
                    else -> {
                        NetworkResponse.Failure(true, null, null)
                    }
                }

            }
        }
    }


}