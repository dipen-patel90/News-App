package com.ct.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ct.MainCoroutineRule
import com.ct.api.APIResponse
import com.ct.extention.getValueBlockedOrNull
import com.ct.model.dto.response.NewsHeadline
import com.ct.repository.NewsRepository
import com.ct.utils.FileUtils
import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val instant = InstantTaskExecutorRule()

    @Mock
    lateinit var repository: NewsRepository


    lateinit var sut: HomeViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        sut = HomeViewModel(repository)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun test_null_body_response() = runTest {
        Mockito.`when`(repository.getTopHeadlines()).thenReturn(Response.success(null))
        val sut = HomeViewModel(repository)
        sut.getTopHeadlines()
        advanceUntilIdle()
        val result = sut.newsHeadlines.getValueBlockedOrNull()

        assertThat(result, instanceOf(APIResponse.Failure::class.java))
    }

    @Test
    fun test_getTopHeadlines() = runTest {
        read_news_response()

        val result = sut.newsHeadlines.getValueBlockedOrNull()
        assertThat(result, instanceOf(APIResponse.Success::class.java))
        Assert.assertEquals(9, result?.data?.size)
        Assert.assertEquals("Latest News", result?.data?.first()?.title)
        Assert.assertEquals("Oldest News", result?.data?.last()?.title)
    }

    @Test
    fun test_update_and_clear_ListSelection() = runTest {
        read_news_response()

        val selectIndex = 3;

        val result = sut.newsHeadlines.getValueBlockedOrNull()
        result?.data?.getOrNull(selectIndex)?.let {
            sut.updateListSelection(it)
            advanceUntilIdle()

            val isSelected =
                sut.newsHeadlines.getValueBlockedOrNull()?.data?.getOrNull(selectIndex)?.isSelected
            Assert.assertEquals(true, isSelected)

            sut.clearListSelection()
            advanceUntilIdle()

            val shouldBeNull =
                sut.newsHeadlines.getValueBlockedOrNull()?.data?.firstOrNull { it.isSelected }
            Assert.assertNull(shouldBeNull)
        }
    }

    private fun read_news_response() = runTest {
        // "news_response_bbc.json" contain 10 news node but we have removed one node title to verify our null check for title
        val articalJson = FileUtils.readFile("/news_response_bbc.json")

        val newsHeadline = Gson().fromJson(
            articalJson,
            NewsHeadline::class.java
        )

        Mockito.`when`(repository.getTopHeadlines()).thenReturn(Response.success(newsHeadline))
        sut.getTopHeadlines()
        advanceUntilIdle()
    }
}