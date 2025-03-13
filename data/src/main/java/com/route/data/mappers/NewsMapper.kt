package com.route.data.mappers

import com.route.data.api.model.ArticlesItem
import com.route.data.api.model.NewsResponse
import com.route.domain.entity.ArticlesItemEntity
import com.route.domain.entity.NewsResponseEntity

fun NewsResponse.toEntity(): NewsResponseEntity {
    return NewsResponseEntity(articles?.map {
        it.toEntity()
    }, message)
}

fun ArticlesItem.toEntity(): ArticlesItemEntity {
    return ArticlesItemEntity(publishedAt, author, urlToImage, description, title, url, content)
}
