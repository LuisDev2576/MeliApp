package com.luis.dev.meliapp.features.results.data.repository

import Paging
import SearchResponse
import SearchResultItem
import android.util.Log
import com.luis.dev.meliapp.features.results.data.datasource.ResultsDataSource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ResultsRepositoryImplTest {

    private val mockDataSource = mockk<ResultsDataSource>()
    private lateinit var repository: ResultsRepositoryImpl

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        mockkStatic(Log::class)
        coEvery { Log.d(any(), any()) } returns 0
        coEvery { Log.e(any(), any()) } returns 0

        Dispatchers.setMain(testDispatcher)

        repository = ResultsRepositoryImpl(
            dataSource = mockDataSource,
            defaultSiteId = "MLA"
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkStatic(Log::class)
    }

    @Test
    fun `searchItems calls dataSource with siteId and returns SearchResponse`() = runTest {
        // Dado
        val query = "iphone"
        val fakeResults = listOf<SearchResultItem>()
        val fakeResponse = SearchResponse(
            siteId = "MLA",
            query = query,
            paging = Paging(total = 0, offset = 0, limit = 50, primaryResults = 0),
            results = fakeResults
        )

        coEvery { mockDataSource.searchItems("MLA", query) } returns fakeResponse

        // Cuando
        val result = repository.searchItems(query)

        // Entonces
        coVerify(exactly = 1) { mockDataSource.searchItems("MLA", query) }
        assertEquals(fakeResponse, result)
    }
}