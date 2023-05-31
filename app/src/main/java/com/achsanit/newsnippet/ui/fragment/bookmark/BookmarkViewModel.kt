package com.achsanit.newsnippet.ui.fragment.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.achsanit.newsnippet.data.NewsRepository
import com.achsanit.newsnippet.data.local.model.NewsEntity

class BookmarkViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {
    fun getListBookmark(): LiveData<List<NewsEntity>> {
        return newsRepository.getListBookmark()
    }
}