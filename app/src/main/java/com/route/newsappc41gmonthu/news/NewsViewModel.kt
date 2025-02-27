package com.route.newsappc41gmonthu.news

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.route.newsappc41gmonthu.api.ApiManager
import com.route.newsappc41gmonthu.api.model.ArticlesItem
import com.route.newsappc41gmonthu.api.model.NewsResponse
import com.route.newsappc41gmonthu.api.model.SourcesItem
import com.route.newsappc41gmonthu.api.model.SourcesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel : ViewModel() {
    val sourcesList = mutableStateListOf<SourcesItem>()
    val articlesList = mutableStateListOf<ArticlesItem>()
    val selectedSourceId = mutableStateOf("")
    val errorState = mutableStateOf("")
    val isLoading = mutableStateOf(false)
    // LiveData
    // StateFlow

    fun getSources(
        categoryAPIKey: String,
    ) {
        ApiManager.newsService.getSources(categoryAPIKey)
            .enqueue(object :
                Callback<SourcesResponse> {
                override fun onResponse(
                    call: Call<SourcesResponse>,
                    response: Response<SourcesResponse>
                ) {
                    if (response.isSuccessful) {
                        val list = response.body()?.sources
                        if (list?.isNotEmpty() == true) {
                            sourcesList.addAll(list)
                        }
                    } else {
                        val errorBody = response.errorBody()?.string()
                        val gson = Gson()
                        val sourcesResponse = gson.fromJson(errorBody, SourcesResponse::class.java)
                        errorState.value = "${sourcesResponse.message}"
                    }
                    Log.e("TAG", "onResponse: ${response.body()}")
                }

                override fun onFailure(
                    call: Call<SourcesResponse>,
                    throwable: Throwable
                ) {
                    errorState.value = throwable.message ?: "Something Went Wrong!"
                }


            })
    }


    fun getNewsBySource() {
        if (selectedSourceId.value.isNotEmpty()) {
            isLoading.value = true
            ApiManager.newsService.getNewsBySource(sources = selectedSourceId.value)
                .enqueue(object : Callback<NewsResponse> {
                    override fun onResponse(
                        p0: Call<NewsResponse>,
                        response: Response<NewsResponse>
                    ) {
                        isLoading.value = false
                        val list = response.body()?.articles
                        if (list?.isNotEmpty() == true) {
                            articlesList.addAll(list)
                        }

                    }

                    override fun onFailure(p0: Call<NewsResponse>, throwable: Throwable) {
                        isLoading.value = false
                        errorState.value = throwable.message ?: "Something went wrong!"
                    }
                })
        }
    }
}
