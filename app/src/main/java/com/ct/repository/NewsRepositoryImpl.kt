package com.ct.repository

import com.ct.api.ApiInterface
import com.ct.model.dto.response.NewsHeadline
import retrofit2.Response
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(private val apiInterface: ApiInterface) :
    NewsRepository {

    override suspend fun getTopHeadlines(): Response<NewsHeadline> {
        return apiInterface.getTopHeadlines()
    }
}