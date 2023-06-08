package com.ct.api

import com.ct.utils.FileUtils
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiInterfaceTest {

    lateinit var mockWebServer: MockWebServer
    lateinit var mockApiInterface: ApiInterface

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockApiInterface = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun test_getTopHeadlinesAPI() = runTest {
        val articalJson = FileUtils.readFile("/news_response_bbc.json")

        val mockResponse = MockResponse()
        mockResponse.setResponseCode(200)
        mockResponse.setBody(articalJson)

        mockWebServer.enqueue(mockResponse)

        val response = mockApiInterface.getTopHeadlines()
        mockWebServer.takeRequest(1, TimeUnit.SECONDS)

        Assert.assertNotNull(response.body())
    }
}