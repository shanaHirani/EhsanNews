package com.jetbrains.handson.mpp.ehsan.ui.homePage

import com.jetbrains.handson.mpp.ehsan.data.repository.FakeNewsRepo
import org.junit.Before
import org.junit.Test

class HomePageViewModelTest {

    private lateinit var viewModel: HomePageViewModel
    val fakeNewsRepo= FakeNewsRepo()

    @Before
    fun setup(){
        viewModel = HomePageViewModel(fakeNewsRepo)
    }

    @Test
    fun if_repo_get_weather_info_return_error_apiStatus_is_error(){
        fakeNewsRepo.setup(getWeatherInfoSuccess = false, getNewsSuccess = true)
        viewModel.getWeatherInf()
    }

}