package com.jetbrains.handson.mpp.ehsan.ui.homePage

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.jetbrains.handson.mpp.ehsan.MainCoroutineRule
import com.jetbrains.handson.mpp.ehsan.data.repository.FakeNewsRepo
import com.jetbrains.handson.mpp.ehsan.getOrAwaitValueTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.TimeoutException

//we have to tell this test use coroutine
@ExperimentalCoroutinesApi
class HomePageViewModelTest {

    private lateinit var viewModel: HomePageViewModel
    private val fakeNewsRepo= FakeNewsRepo()
    // because here is test folder we need to do every thing in the main thread
    //this two rules help us to do that
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup(){
        viewModel = HomePageViewModel(fakeNewsRepo)
    }

    @Test
    fun if_repo_get_weather_info_return_error_apiStatusEvent_is_not_null(){
        fakeNewsRepo.setup(getWeatherInfoSuccess = false, getNewsSuccess = true)
        viewModel.getWeatherInf()
        val value= viewModel.apiError.getOrAwaitValueTest()
        assertThat(value.peekContent()).isNotEqualTo(null)
    }

   @Test(expected = TimeoutException::class)
    fun if_repo_get_weather_info_not_return_error_apiStatusEvent_is_null(){
        fakeNewsRepo.setup(getWeatherInfoSuccess = true, getNewsSuccess = true)
        viewModel.getWeatherInf()
        val value= viewModel.apiError.getOrAwaitValueTest()
    }

    @Test(expected = TimeoutException::class)
    fun if_repo_get_weather_info_return_error_weatherInfo_is_null(){
        fakeNewsRepo.setup(getWeatherInfoSuccess = false, getNewsSuccess = true)
        viewModel.getWeatherInf()
        val value= viewModel.weatherInfo.getOrAwaitValueTest()

    }

    @Test
    fun if_repo_get_weather_info_not_return_error_weatherInfo_is_not_null(){
        fakeNewsRepo.setup(getWeatherInfoSuccess = true, getNewsSuccess = true)
        viewModel.getWeatherInf()
        val value= viewModel.weatherInfo.getOrAwaitValueTest()
        assertThat(value).isNotEqualTo(null)
    }

}