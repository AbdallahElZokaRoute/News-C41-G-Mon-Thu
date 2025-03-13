package com.route.data.di

import com.route.data.api.NewsService
import com.route.data.dataSource.online.NewsOnlineDataSourceImpl
import com.route.data.repository.NewsRepositoryImpl
import com.route.domain.repositories.NewsOnlineDataSource
import com.route.domain.repositories.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoriesModule {
    @Singleton
    @Provides
    fun provideNewsOnlineDataSource(
        newsService: NewsService
    ): NewsOnlineDataSource {
        return NewsOnlineDataSourceImpl(newsService)
    }

    @Singleton
    @Provides
    fun provideNewsRepository(
        onlineDataSource: NewsOnlineDataSource
    ): NewsRepository {
        return NewsRepositoryImpl(onlineDataSource)
    }

}
