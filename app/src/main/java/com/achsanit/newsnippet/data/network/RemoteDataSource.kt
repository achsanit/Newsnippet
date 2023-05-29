package com.achsanit.newsnippet.data.network

import com.achsanit.newsnippet.data.local.model.NewsEntity
import com.achsanit.newsnippet.utils.Resource
import com.achsanit.newsnippet.utils.map
import com.achsanit.newsnippet.utils.resourceMapper

class RemoteDataSource(private val newsService: NewsService) {
    suspend fun getNews(
        query: HashMap<String, String>
    ): Resource<List<NewsEntity>?> {
        return resourceMapper {
            newsService.getTopHeadlines(query).map()
        }
    }

}