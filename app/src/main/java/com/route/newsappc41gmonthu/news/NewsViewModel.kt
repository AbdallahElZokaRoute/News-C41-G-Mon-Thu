package com.route.newsappc41gmonthu.news

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.route.domain.entity.ArticlesItemEntity
import com.route.domain.entity.SourcesItemEntity
import com.route.domain.usecase.GetNewsBySourceUseCase
import com.route.domain.usecase.GetSourcesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getSourcesUseCase: GetSourcesUseCase,
    private val getArticlesBySourceUseCase: GetNewsBySourceUseCase,
) : ViewModel() {
    // Clean Architecture
    // Dependency Injection   ->
    val sourcesList = mutableStateListOf<SourcesItemEntity>()
    val articlesList = mutableStateListOf<ArticlesItemEntity>()
    val selectedSourceId = mutableStateOf("")
    val errorState = mutableStateOf("")
    val isLoading = mutableStateOf(false)

    fun getSources(
        categoryAPIKey: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = getSourcesUseCase.invoke(categoryAPIKey)
                if (response.isNotEmpty()) {
                    sourcesList.addAll(response)
                }

            } catch (e: Exception) {
                errorState.value = e.message ?: "Something Went Wrong!"
            }
        }
    }


    fun getNewsBySource() {
        if (selectedSourceId.value.isNotEmpty()) {
            isLoading.value = true
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val articles = getArticlesBySourceUseCase.invoke(selectedSourceId.value)
                    isLoading.value = false
                    if (articles.isNotEmpty()) {
                        articlesList.clear()
                        articlesList.addAll(articles)
                    }
                } catch (e: Exception) {
                    isLoading.value = false
                    errorState.value = e.message ?: "Something Went Wrong!"
                }
            }
        }
    }
}
