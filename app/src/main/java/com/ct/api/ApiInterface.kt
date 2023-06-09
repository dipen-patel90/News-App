package com.ct.api

import com.ct.BuildConfig
import com.ct.Config
import com.ct.model.dto.response.NewsHeadline
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("top-headlines/")
    suspend fun getTopHeadlines(
        @Query("sources") sources: String = Config.NEWS_SOURCES,
        @Query("apiKey") apiKey: String = BuildConfig.NEWS_API_KEY
    ): Response<NewsHeadline>
}