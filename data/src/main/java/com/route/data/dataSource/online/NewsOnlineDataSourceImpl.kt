package com.route.data.dataSource.online

import com.route.data.api.NewsService
import com.route.data.mappers.toEntity
import com.route.domain.entity.ArticlesItemEntity
import com.route.domain.entity.SourcesItemEntity
import com.route.domain.repositories.NewsOnlineDataSource

class NewsOnlineDataSourceImpl(
    private val newsService: NewsService
) : NewsOnlineDataSource {
    override suspend fun getSources(categoryId: String): List<SourcesItemEntity> {
        return newsService.getSources(categoryId).body()?.sources?.map { it.toEntity() }
            ?: emptyList()
    }

    override suspend fun getNewsBySource(sourceId: String): List<ArticlesItemEntity> {
        return newsService.getNewsBySource(sourceId).body()?.articles?.map { it.toEntity() }
            ?: emptyList()
    }

}