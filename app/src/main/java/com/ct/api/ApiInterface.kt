package com.ct.api

import com.ct.BuildConfig
import com.ct.Config
import com.ct.NewsApp
import com.ct.model.dto.response.NewsHeadline
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiInterface {

    companion object {
        private val NEWS_URL = NewsApp.currentEnvironment.newsUrl

        val TOP_HEADLINES by NewsAppEnvironment(NEWS_URL, "top-headlines")
    }

    @GET
    suspend fun getTopHeadlines(
        @Url url: String = TOP_HEADLINES,
        @Query("sources") sources: String = Config.NEWS_SOURCES,
        @Query("apiKey") apiKey: String = BuildConfig.NEWS_API_KEY
    ): Response<NewsHeadline>
}