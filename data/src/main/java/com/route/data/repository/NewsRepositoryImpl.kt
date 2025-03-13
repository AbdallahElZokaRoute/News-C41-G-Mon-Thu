package com.route.data.repository

import com.route.domain.entity.ArticlesItemEntity
import com.route.domain.entity.SourcesItemEntity
import com.route.domain.repositories.NewsOnlineDataSource
import com.route.domain.repositories.NewsRepository

class NewsRepositoryImpl(
    private val onlineDataSource: NewsOnlineDataSource
) : NewsRepository {
    override suspend fun getSources(categoryId: String): List<SourcesItemEntity> {
        return onlineDataSource.getSources(categoryId)
    }

    override suspend fun getNewsBySource(sourceId: String): List<ArticlesItemEntity> {
        return onlineDataSource.getNewsBySource(sourceId)
    }

}
