package com.luis.dev.meliapp.features.results.domain.usecases

import Paging
import android.util.Log
import SearchResponse
import SearchResultItem
import com.luis.dev.meliapp.features.results.data.repository.ResultsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetResultsUseCaseImplTest {

    private val mockRepository = mockk<ResultsRepository>()
    private lateinit var useCase: GetResultsUseCaseImpl

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        mockkStatic(Log::class)
        coEvery { Log.d(any(), any()) } returns 0
        coEvery { Log.e(any(), any()) } returns 0

        Dispatchers.setMain(testDispatcher)
        useCase = GetResultsUseCaseImpl(mockRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkStatic(Log::class)
    }

    @Test
    fun `execute calls repository and returns list of items`() = runTest {
        // Dado
        val query = "tv"
        val fakeResults = listOf(
            SearchResultItem(
                id = "111",
                title = "Televisor 1",
                price = 1000.0,
                thumbnail = "http://fakeimage.com/1.jpg",
                permalink = "",
                officialStoreName = null,
                originalPrice = null,
                shipping = null,
                installments = null,
                attributes = emptyList()
            )
        )
        val fakeResponse = SearchResponse(
            siteId = "MLA",
            query = query,
            paging = Paging(total = 1, offset = 0, limit = 50, primaryResults = 1),
            results = fakeResults
        )

        coEvery { mockRepository.searchItems(query) } returns fakeResponse

        // Cuando
        val result = useCase.execute(query)

        // Entonces
        coVerify(exactly = 1) { mockRepository.searchItems(query) }
        assertEquals(fakeResults, result)
    }
}
