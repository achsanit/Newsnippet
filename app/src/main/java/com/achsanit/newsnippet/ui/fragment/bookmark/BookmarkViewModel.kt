package com.achsanit.newsnippet.ui.fragment.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.achsanit.newsnippet.data.NewsRepository
import com.achsanit.newsnippet.data.local.model.BookmarkEntity
import com.achsanit.newsnippet.data.local.model.NewsEntity
import kotlinx.coroutines.launch

class BookmarkViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {
    fun getListBookmark(): LiveData<List<BookmarkEntity>> {
        return newsRepository.getListBookmark()
    }

    fun deleteBookmark(data: NewsEntity) {
        viewModelScope.launch {
            newsRepository.deleteBookmark(data.title)
        }
    }

    fun deleteAllBookmarks() {
        viewModelScope.launch {
            newsRepository.deleteAllBookmarks()
        }
    }
}