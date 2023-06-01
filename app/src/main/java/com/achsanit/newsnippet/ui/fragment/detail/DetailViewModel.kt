package com.achsanit.newsnippet.ui.fragment.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.achsanit.newsnippet.data.NewsRepository
import com.achsanit.newsnippet.data.local.model.BookmarkEntity
import com.achsanit.newsnippet.data.local.model.NewsEntity
import com.achsanit.newsnippet.utils.mapToBookmark
import kotlinx.coroutines.launch

class DetailViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {

    fun addBookmark(data: NewsEntity) {
        viewModelScope.launch {
            newsRepository.addBookmark(data.mapToBookmark())
        }
    }

    fun getBookmarkByTitle(title: String): LiveData<BookmarkEntity> {
        return newsRepository.getBookmarkByTitle(title)
    }

    fun deleteBookmark(data: NewsEntity) {
        viewModelScope.launch {
            newsRepository.deleteBookmark(data.title)
        }
    }

}