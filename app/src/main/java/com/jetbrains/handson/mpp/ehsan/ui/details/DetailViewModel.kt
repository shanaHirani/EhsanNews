package com.jetbrains.handson.mpp.ehsan.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.jetbrains.handson.mpp.ehsan.data.model.News
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(val state:SavedStateHandle) : ViewModel() {

    private val _selectedNews = MutableLiveData<News>()
    val selectedNews:LiveData<News>
    get() = _selectedNews

    init {
        _selectedNews.value = state.get<News>("News")
    }
}