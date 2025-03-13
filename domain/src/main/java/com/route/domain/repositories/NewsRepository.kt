package com.route.domain.repositories

import com.route.domain.entity.ArticlesItemEntity
import com.route.domain.entity.SourcesItemEntity

interface NewsRepository {
    suspend fun getSources(categoryId: String): List<SourcesItemEntity>
    suspend fun getNewsBySource(sourceId: String): List<ArticlesItemEntity>
}

interface NewsOnlineDataSource {
    suspend fun getSources(categoryId: String): List<SourcesItemEntity>
    suspend fun getNewsBySource(sourceId: String): List<ArticlesItemEntity>
}

