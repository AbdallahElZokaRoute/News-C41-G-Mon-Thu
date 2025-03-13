package com.route.domain.usecase

import com.route.domain.entity.ArticlesItemEntity
import com.route.domain.repositories.NewsRepository
import javax.inject.Inject

class GetNewsBySourceUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    suspend fun invoke(sourceId: String): List<ArticlesItemEntity> {
        return repository.getNewsBySource(sourceId)
    }

}