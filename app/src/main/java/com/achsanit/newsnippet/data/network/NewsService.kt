package com.achsanit.newsnippet.data.network

import com.achsanit.newsnippet.data.network.response.NewsResponse
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface NewsService {
    /**
     *  Get headlines news
     */
    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @QueryMap queryMap: HashMap<String, String>
    ): NewsResponse

}