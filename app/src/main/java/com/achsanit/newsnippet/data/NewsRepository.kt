package com.achsanit.newsnippet.data

import com.achsanit.newsnippet.data.local.model.NewsEntity
import com.achsanit.newsnippet.data.network.RemoteDataSource
import com.achsanit.newsnippet.utils.Resource

class NewsRepository(
    private val remoteDataSource: RemoteDataSource
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
}