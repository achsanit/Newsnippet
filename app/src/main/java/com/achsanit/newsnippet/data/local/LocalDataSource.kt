package com.achsanit.newsnippet.data.local

import androidx.lifecycle.LiveData
import com.achsanit.newsnippet.data.local.model.NewsEntity
import com.achsanit.newsnippet.data.local.room.BookmarkDao

class LocalDataSource(private val dao: BookmarkDao) {
    suspend fun insertBookmark(data: NewsEntity) {
        dao.insertBookmark(data)
    }
    suspend fun deleteBookmark(title: String) {
        dao.deleteBookmarkNews(title)
    }
    fun getListBookmark(): LiveData<List<NewsEntity>> {
        return dao.getBookmark()
    }
    fun getBookmarkByTitle(title: String): LiveData<NewsEntity> {
        return dao.getBookmarkByTitle(title)
    }
}