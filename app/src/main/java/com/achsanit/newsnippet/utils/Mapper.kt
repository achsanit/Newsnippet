package com.achsanit.newsnippet.utils

import com.achsanit.newsnippet.data.local.model.NewsEntity
import com.achsanit.newsnippet.data.network.response.NewsResponse

fun NewsResponse.map(): List<NewsEntity>? {
    return this.articles?.map {
        NewsEntity(
            publishedAt = it?.publishedAt ?: "",
            title = it?.title ?: "",
            urlToImage = it?.urlToImage ?: "",
            url = it?.url ?: "",
            description = it?.description ?: "",
            source = it?.source?.name ?: "",
            content = it?.content ?: "",
            author = it?.author ?: ""
        )
    }
}