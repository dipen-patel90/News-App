package com.ct.repository

import com.ct.api.ApiInterface
import com.ct.utils.FileUtils
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NewsRepositoryImplTest {

    lateinit var mockWebServer: MockWebServer
    lateinit var mockApiInterface: ApiInterface
    lateinit var newsRepository: NewsRepositoryImpl

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        mockApiInterface = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
        newsRepository = NewsRepositoryImpl(mockApiInterface)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun test_getTopHeadlines_Success_Case() = runTest {
        val articleJson = FileUtils.readFile("/news_response_bbc.json")

        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(articleJson)

        mockWebServer.enqueue(mockResponse)

        val response = newsRepository.getTopHeadlines()
        mockWebServer.takeRequest(1, TimeUnit.SECONDS)

        assertEquals(true, response.isSuccessful)
        assertNotNull(response.body())
        assertEquals(10, response.body()?.articles?.size)
    }

    @Test
    fun test_getTopHeadlines_Failure_Case() = runTest {

        val mockResponse = MockResponse()
            .setResponseCode(404)

        mockWebServer.enqueue(mockResponse)

        val response = newsRepository.getTopHeadlines()
        mockWebServer.takeRequest(1, TimeUnit.SECONDS)

        assertEquals(false, response.isSuccessful)
    }

}