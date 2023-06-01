package com.achsanit.newsnippet.utils

import com.achsanit.newsnippet.data.local.model.BookmarkEntity
import com.achsanit.newsnippet.data.local.model.NewsEntity
import com.achsanit.newsnippet.data.local.model.TopHeadlinesEntity
import com.achsanit.newsnippet.data.network.response.NewsResponse

fun NewsResponse.mapToNewsCat(): List<NewsEntity>? {
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

fun NewsResponse.mapToTopHeadline(): List<TopHeadlinesEntity>? {
    return this.articles?.map {
        TopHeadlinesEntity(
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

fun TopHeadlinesEntity.mapToNewsEntity(): NewsEntity {
    return this.let {
        NewsEntity(
            publishedAt = it.publishedAt,
            title = it.title,
            urlToImage = it.urlToImage,
            url = it.url,
            description = it.description,
            source = it.source,
            content = it.content,
            author = it.author
        )
    }
}

fun List<BookmarkEntity>.mapToNewsEntity(): List<NewsEntity> {
    return this.map {
        NewsEntity(
            publishedAt = it.publishedAt,
            title = it.title,
            urlToImage = it.urlToImage,
            url = it.url,
            description = it.description,
            source = it.source,
            content = it.content,
            author = it.author
        )
    }
}

fun NewsEntity.mapToBookmark(): BookmarkEntity {
    return this.let {
        BookmarkEntity(
            publishedAt = it.publishedAt,
            title = it.title,
            urlToImage = it.urlToImage,
            url = it.url,
            description = it.description,
            source = it.source,
            content = it.content,
            author = it.author
        )
    }
}