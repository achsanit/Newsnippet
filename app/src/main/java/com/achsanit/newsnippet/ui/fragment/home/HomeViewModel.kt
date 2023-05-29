package com.achsanit.newsnippet.ui.fragment.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.achsanit.newsnippet.data.NewsRepository
import com.achsanit.newsnippet.data.local.model.NewsEntity
import com.achsanit.newsnippet.utils.Resource
import kotlinx.coroutines.launch

class HomeViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private val _listBanner: MutableLiveData<Resource<List<NewsEntity>?>> = MutableLiveData(Resource.Loading())
    val listBanner: LiveData<Resource<List<NewsEntity>?>> get() = _listBanner

    private val _listNewsByCategory: MutableLiveData<Resource<List<NewsEntity>?>> = MutableLiveData(Resource.Loading())
    val listNewsByCategory: LiveData<Resource<List<NewsEntity>?>> get() = _listNewsByCategory

    fun getBanner() {
        viewModelScope.launch {
            _listBanner.postValue(newsRepository.getTopHeadline(10))
        }
    }

    fun getNewsByCategory(
        category: String
    ) {
        _listNewsByCategory.postValue(Resource.Loading())
        viewModelScope.launch {
            _listNewsByCategory.postValue(
                newsRepository.getNewsByCategory(category, 30)
            )
        }
    }

}