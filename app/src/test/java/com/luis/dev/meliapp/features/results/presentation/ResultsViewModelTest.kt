package com.luis.dev.meliapp.features.results.presentation

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import SearchResultItem
import com.luis.dev.meliapp.features.results.domain.usecases.GetResultsUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ResultsViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: ResultsViewModel
    private val mockGetResultsUseCase = mockk<GetResultsUseCase>()

    @Before
    fun setup() {
        mockkStatic(Log::class)
        coEvery { Log.d(any(), any()) } returns 0
        coEvery { Log.e(any(), any()) } returns 0

        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkStatic(Log::class)
    }

    @Test
    fun `init should fetch results and update state on success`() = runTest {
        val product = "Nintendo Switch"
        val savedStateHandle = SavedStateHandle(mapOf("productName" to product))

        // Dado una lista de resultados falsos
        val fakeResults = listOf(
            SearchResultItem(
                id = "123",
                title = "Switch",
                price = 299.99,
                thumbnail = "http://image.jpg",
                permalink = "",
                officialStoreName = null,
                originalPrice = null,
                shipping = null,
                installments = null,
                attributes = emptyList()
            ),
            SearchResultItem(
                id = "456",
                title = "Switch Lite",
                price = 199.99,
                thumbnail = "http://image2.jpg",
                permalink = "",
                officialStoreName = null,
                originalPrice = null,
                shipping = null,
                installments = null,
                attributes = emptyList()
            )
        )

        coEvery { mockGetResultsUseCase.execute(product) } returns fakeResults

        // Cuando se crea el ViewModel (init dispara fetchResultsFor)
        viewModel = ResultsViewModel(mockGetResultsUseCase, savedStateHandle)
        testDispatcher.scheduler.advanceUntilIdle()

        // Verificamos que se haya llamado al useCase
        coVerify(exactly = 1) { mockGetResultsUseCase.execute(product) }

        // Chequeamos el estado
        val currentState = viewModel.state.first()
        assertFalse(currentState.isLoading)
        assertNull(currentState.error)
        assertEquals(fakeResults, currentState.results)
    }

    @Test
    fun `init should update state with error if useCase throws exception`() = runTest {
        val product = "PlayStation 5"
        val savedStateHandle = SavedStateHandle(mapOf("productName" to product))

        val errorMessage = "Network error"
        coEvery { mockGetResultsUseCase.execute(product) } throws Exception(errorMessage)

        // Cuando se crea el ViewModel
        viewModel = ResultsViewModel(mockGetResultsUseCase, savedStateHandle)
        testDispatcher.scheduler.advanceUntilIdle()

        val currentState = viewModel.state.first()
        assertFalse(currentState.isLoading)
        assertTrue(currentState.results.isEmpty())
        assertEquals(errorMessage, currentState.error)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `init should throw if productName is missing`() = runTest {
        // No se provee productName
        val savedStateHandle = SavedStateHandle()

        // Cuando se instancia
        viewModel = ResultsViewModel(mockGetResultsUseCase, savedStateHandle)
    }

    @Test
    fun `fetchResultsFor triggers the useCase call`() = runTest {
        val product = "Xbox"
        val savedStateHandle = SavedStateHandle(mapOf("productName" to product))
        // Lista vacía simula respuesta
        coEvery { mockGetResultsUseCase.execute(product) } returns emptyList()

        // Instanciamos
        viewModel = ResultsViewModel(mockGetResultsUseCase, savedStateHandle)
        testDispatcher.scheduler.advanceUntilIdle()

        // Forzamos una llamada extra a fetchResultsFor
        viewModel.fetchResultsFor()
        testDispatcher.scheduler.advanceUntilIdle()

        // Se llama 2 veces en total
        coVerify(exactly = 2) { mockGetResultsUseCase.execute(product) }
    }
}
