package com.achsanit.newsnippet.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.asFlow
import com.achsanit.newsnippet.data.local.LocalDataSource
import com.achsanit.newsnippet.data.local.model.BookmarkEntity
import com.achsanit.newsnippet.data.local.model.NewsEntity
import com.achsanit.newsnippet.data.local.model.TopHeadlinesEntity
import com.achsanit.newsnippet.data.network.RemoteDataSource
import com.achsanit.newsnippet.data.network.response.NewsResponse
import com.achsanit.newsnippet.utils.ApiResponse
import com.achsanit.newsnippet.utils.InternetConnectionHelper.isOnline
import com.achsanit.newsnippet.utils.Resource
import com.achsanit.newsnippet.utils.mapToNewsCat
import com.achsanit.newsnippet.utils.mapToTopHeadline
import kotlinx.coroutines.flow.Flow

class NewsRepository(
    private val context: Context,
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {
    fun getTopHeadline(
        pageSize: Int
    ): Flow<Resource<List<TopHeadlinesEntity>>> =
        object : NetworkBoundResource<List<TopHeadlinesEntity>,NewsResponse>() {
            override fun loadFromDB(): Flow<List<TopHeadlinesEntity>> {
                return localDataSource.getLatestTopHeadlines().asFlow()
            }

            override suspend fun createCall(): Flow<ApiResponse<NewsResponse>> {
                val query = HashMap<String, String>()
                query["country"] = "us"
                query["page_size"] = pageSize.toString()

                return remoteDataSource.getNews(query)
            }

            override suspend fun saveCallResult(data: NewsResponse) {
                data.mapToTopHeadline()?.let {
                    localDataSource.insertLatestTopHeadlines(it)
                }
            }

            override fun showError(data: List<TopHeadlinesEntity>?): Boolean {
                return data.isNullOrEmpty()
            }

            override fun shouldFetch(data: List<TopHeadlinesEntity>?): Boolean {
                return isOnline(context) || data.isNullOrEmpty()
            }

        }.asFlow()

    fun getNewsByCategory(
        category: String,
        pageSize: Int
    ): Flow<Resource<List<NewsEntity>>> =
        object : NetworkBoundResource<List<NewsEntity>,NewsResponse>() {
            override fun loadFromDB(): Flow<List<NewsEntity>> {
                return localDataSource.getLatestNewsByCat().asFlow()
            }

            override suspend fun createCall(): Flow<ApiResponse<NewsResponse>> {
                val query = HashMap<String, String>()
                query["country"] = "us"
                query["category"] = category
                query["page_size"] = pageSize.toString()

                return remoteDataSource.getNews(query)
            }

            override fun onFetchFailed(errorMessage: String) { }

            override suspend fun saveCallResult(data: NewsResponse) {
                data.mapToNewsCat()?.let {
                    localDataSource.insertLatestNewsByCat(it)
                }
            }

            override fun showError(data: List<NewsEntity>?): Boolean {
                return data.isNullOrEmpty()
            }

            override fun shouldFetch(data: List<NewsEntity>?): Boolean {
                return isOnline(context) || data.isNullOrEmpty()
            }

        }.asFlow()

    suspend fun addBookmark(data: BookmarkEntity) {
        return localDataSource.insertBookmark(data)
    }
    suspend fun deleteBookmark(title: String) {
        return localDataSource.deleteBookmark(title)
    }
    suspend fun deleteAllBookmarks() {
        return localDataSource.deleteAllBookmarks()
    }
    fun getListBookmark(): LiveData<List<BookmarkEntity>> {
        return localDataSource.getListBookmark()
    }
    fun getBookmarkByTitle(title: String): LiveData<BookmarkEntity> {
        return localDataSource.getBookmarkByTitle(title)
    }

}