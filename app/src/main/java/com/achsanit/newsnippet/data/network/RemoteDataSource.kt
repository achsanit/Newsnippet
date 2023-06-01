package com.achsanit.newsnippet.data.network

import com.achsanit.newsnippet.data.network.response.NewsResponse
import com.achsanit.newsnippet.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

class RemoteDataSource(private val newsService: NewsService) {
    suspend fun getNews(
        query: HashMap<String, String>
    ): Flow<ApiResponse<NewsResponse>> =
        object : CallApiAdapter<NewsResponse>() {
            override suspend fun createCall(): NewsResponse {
                return newsService.getNews(query)
            }
        }.asFlow()

}