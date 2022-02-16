package com.jetbrains.handson.mpp.ehsan.data.repository

import com.google.common.truth.Truth
import com.jetbrains.handson.mpp.ehsan.data.remote.RemoteDataSource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class NewsRepositoryTest {
    private lateinit var mockRemoteDataSource: RemoteDataSource
    private lateinit var mockStringProvider: StringResProvider
    private lateinit var mockTimeProvider: TimeProvider
    private val dateRawFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
    private lateinit var repository: NewsRepository

    @Before
    fun setUp() {
        mockRemoteDataSource = mockk()
        mockStringProvider = mockk()
        mockTimeProvider = mockk()
        repository = NewsRepository(mockRemoteDataSource, mockStringProvider, mockTimeProvider)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getNews() {
    }

    @Test
    fun getWeatherInfo() {
    }

    @Test
    fun getTwoDayAgo1() {

        coEvery {
            mockTimeProvider.getNowDate()
        } returns LocalDate.parse("2021-10-15T02:01:43Z", dateRawFormat)

        runBlocking {
            //Truth.assertThat(repository.getTwoDayAgo())
            //    .isEqualTo("13-10-2021")
        }
    }

    @Test
    fun getTwoDayAgo2() {

        coEvery {
            mockTimeProvider.getNowDate()
        } returns LocalDate.parse("2021-10-01T02:01:43Z", dateRawFormat)

        runBlocking {
           // Truth.assertThat(repository.getTwoDayAgo())
           //     .isEqualTo("29-09-2021")
        }
    }
}