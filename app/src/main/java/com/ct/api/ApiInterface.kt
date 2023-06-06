package com.ct.api

import com.ct.BuildConfig
import com.ct.Config
import com.ct.NewsApp
import com.ct.extention.empty
import com.ct.model.dto.response.NewsHeadline
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

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


    class NewsAppEnvironment(
        private val baseUrl: String,
        private var urlPath: String,
        private val pathMap: Map<String, String>? = null
    ) : ReadWriteProperty<Any?, String> {

        private var completeUrl: String = String.empty()

        override fun getValue(thisRef: Any?, property: KProperty<*>): String {
            replacePathValues()
            return baseUrl + urlPath
        }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
            replacePathValues()
            completeUrl = baseUrl + urlPath
        }

        private fun replacePathValues() {
            if (!pathMap.isNullOrEmpty()) {
                pathMap.keys.forEach { key ->
                    pathMap[key]?.let {
                        urlPath = urlPath.replace(key, it)
                    }
                }
            }
        }
    }
}