package com.ct.repository

import com.ct.model.dto.response.NewsHeadline
import retrofit2.Response

interface NewsRepository {

    suspend fun getTopHeadlines(): Response<NewsHeadline>

}