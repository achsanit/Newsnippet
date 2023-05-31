package com.achsanit.newsnippet.data

import androidx.lifecycle.LiveData
import com.achsanit.newsnippet.data.local.LocalDataSource
import com.achsanit.newsnippet.data.local.model.NewsEntity
import com.achsanit.newsnippet.data.network.RemoteDataSource
import com.achsanit.newsnippet.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {
    suspend fun getTopHeadline(
        pageSize: Int
    ): Resource<List<NewsEntity>?> {
        val query = HashMap<String, String>()
        query["country"] = "us"
        query["page_size"] = pageSize.toString()

        return remoteDataSource.getNews(query)
    }

    suspend fun getNewsByCategory(
        category: String,
        pageSize: Int
    ): Resource<List<NewsEntity>?> {
        val query = HashMap<String, String>()
        query["country"] = "us"
        query["category"] = category
        query["page_size"] = pageSize.toString()

        return remoteDataSource.getNews(query)
    }

    suspend fun addBookmark(data: NewsEntity) {
        return localDataSource.insertBookmark(data)
    }
    suspend fun deleteBookmark(title: String) {
        return localDataSource.deleteBookmark(title)
    }
    fun getListBookmark(): LiveData<List<NewsEntity>> {
        return localDataSource.getListBookmark()
    }
    fun getBookmarkByTitle(title: String): LiveData<NewsEntity> {
        return localDataSource.getBookmarkByTitle(title)
    }

    fun addOrDelete(data: NewsEntity) {
        val news = localDataSource.getBookmarkByTitle(data.title).value

        CoroutineScope(Dispatchers.IO).launch {
            news?.let {
                localDataSource.deleteBookmark(data.title)
            } ?: run {
                localDataSource.insertBookmark(data)
            }
        }
    }
}