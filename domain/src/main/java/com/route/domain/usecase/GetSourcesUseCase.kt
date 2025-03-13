package com.route.domain.usecase

import com.route.domain.entity.SourcesItemEntity
import com.route.domain.repositories.NewsRepository
import javax.inject.Inject

class GetSourcesUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    suspend fun invoke(category: String): List<SourcesItemEntity> {
        return repository.getSources(category)
    }
}