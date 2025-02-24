package com.route.newsappc41gmonthu.api

import com.route.newsappc41gmonthu.api.model.NewsResponse
import com.route.newsappc41gmonthu.api.model.SourcesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("top-headlines/sources")
    fun getSources(@Query("apiKey") apiKey: String = "8e30e66ecc364d75967401f639e6f535"): Call<SourcesResponse>

    @GET("everything")
    fun getNewsBySource(
        @Query("sources") sources: String,
        @Query("apiKey") apiKey: String = "8e30e66ecc364d75967401f639e6f535"
    ): Call<NewsResponse>
}
