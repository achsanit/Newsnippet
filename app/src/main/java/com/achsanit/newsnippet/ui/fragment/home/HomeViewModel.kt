package com.achsanit.newsnippet.ui.fragment.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.achsanit.newsnippet.data.NewsRepository
import com.achsanit.newsnippet.data.local.model.NewsEntity
import com.achsanit.newsnippet.data.local.model.TopHeadlinesEntity
import com.achsanit.newsnippet.utils.Resource

class HomeViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {

    fun getBanner(): LiveData<Resource<List<TopHeadlinesEntity>>> {
        return newsRepository.getTopHeadline(10).asLiveData()
    }

    fun getNewsByCategory(
        category: String
    ): LiveData<Resource<List<NewsEntity>>> {
        return newsRepository.getNewsByCategory(category, 30).asLiveData()
    }

}