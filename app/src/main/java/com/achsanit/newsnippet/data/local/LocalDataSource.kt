package com.achsanit.newsnippet.data.local

import androidx.lifecycle.LiveData
import com.achsanit.newsnippet.data.local.model.BookmarkEntity
import com.achsanit.newsnippet.data.local.model.NewsEntity
import com.achsanit.newsnippet.data.local.model.TopHeadlinesEntity
import com.achsanit.newsnippet.data.local.room.NewsnippetDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocalDataSource(private val dao: NewsnippetDao) {
    suspend fun insertBookmark(data: BookmarkEntity) {
        dao.insertBookmark(data)
    }
    suspend fun deleteBookmark(title: String) {
        dao.deleteBookmarkNews(title)
    }
    suspend fun deleteAllBookmarks() {
        dao.deleteAllBookmarks()
    }
    fun getListBookmark(): LiveData<List<BookmarkEntity>> {
        return dao.getBookmark()
    }
    fun getBookmarkByTitle(title: String): LiveData<BookmarkEntity> {
        return dao.getBookmarkByTitle(title)
    }
    fun insertLatestNewsByCat(data: List<NewsEntity>) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.deleteAllNewsByCat()
            dao.insertAllNewsByCat(data)
        }
    }

    fun getLatestNewsByCat(): LiveData<List<NewsEntity>> {
        return dao.getLatestNewsByCat()
    }

    fun insertLatestTopHeadlines(data: List<TopHeadlinesEntity>) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.deleteAllTopHeadline()
            dao.insertAllTopHeadline(data)
        }
    }

    fun getLatestTopHeadlines(): LiveData<List<TopHeadlinesEntity>> {
        return dao.getLatestTopHeadlines()
    }

}