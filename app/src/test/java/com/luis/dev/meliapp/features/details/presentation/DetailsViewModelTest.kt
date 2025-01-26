package com.luis.dev.meliapp.features.details.presentation

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import com.luis.dev.meliapp.features.details.data.model.Attribute
import com.luis.dev.meliapp.features.details.domain.usecases.GetItemDetailUseCase
import com.luis.dev.meliapp.features.details.data.model.ItemDetailResponse
import com.luis.dev.meliapp.features.details.data.model.Picture
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DetailsViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val mockGetItemDetailUseCase = mockk<GetItemDetailUseCase>()
    private lateinit var viewModel: DetailsViewModel

    @Before
    fun setup() {
        // Mockear Log
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
    fun `init should load item detail and update state on success`() = runTest {
        // Dado un productId en el SavedStateHandle
        val productId = "MLA12345"
        val savedStateHandle = SavedStateHandle(mapOf("productId" to productId))

        // Dado que el useCase retorne un objeto válido
        val fakeDetail = ItemDetailResponse(
            id = "MLA1461972881",
            siteId = "MLA",
            title = "Apple iPhone 15 (128 Gb) - Negro",
            price = 1899995.0,
            currencyId = "ARS",
            thumbnail = "http://http2.mlstatic.com/D_779617-MLA71782867320_092023-I.jpg",
            pictures = listOf(
                Picture(id = "779617-MLA71782867320_092023", url = "https://http2.mlstatic.com/D_779617-MLA71782867320_092023-O.jpg")
            ),
            attributes = listOf(
                Attribute(id = "BRAND", name = "Marca", valueName = "Apple"),
                Attribute(id = "COLOR", name = "Color", valueName = "Negro"),
                Attribute(id = "GTIN", name = "Código universal de producto", valueName = "195949035937"),
                Attribute(id = "ITEM_CONDITION", name = "Condición del ítem", valueName = "Nuevo"),
                Attribute(id = "LINE", name = "Línea", valueName = "iPhone 15")
            ),
            warranty = "Garantía de fábrica: 12 meses"
        )
        coEvery { mockGetItemDetailUseCase.execute(productId) } returns fakeDetail

        // Cuando se instancia el ViewModel
        viewModel = DetailsViewModel(mockGetItemDetailUseCase, savedStateHandle)

        // Avanzamos corrutinas
        testDispatcher.scheduler.advanceUntilIdle()

        // Entonces debe haberse llamado al useCase
        coVerify(exactly = 1) { mockGetItemDetailUseCase.execute(productId) }

        // Y el state debe reflejar el detalle y no tener error
        val currentState = viewModel.state.first()
        assertFalse(currentState.isLoading)
        assertEquals(fakeDetail, currentState.itemDetail)
        assertNull(currentState.error)
    }

    @Test
    fun `init should update state with error if useCase throws exception`() = runTest {
        // Dado un productId en el SavedStateHandle
        val productId = "MLA54321"
        val savedStateHandle = SavedStateHandle(mapOf("productId" to productId))

        // Dado que el useCase lance excepción
        val errorMessage = "Error de red"
        coEvery { mockGetItemDetailUseCase.execute(productId) } throws Exception(errorMessage)

        // Cuando se instancia el ViewModel
        viewModel = DetailsViewModel(mockGetItemDetailUseCase, savedStateHandle)

        // Avanzamos corrutinas
        testDispatcher.scheduler.advanceUntilIdle()

        // Entonces el state debe reflejar el error y la carga debe terminar
        val currentState = viewModel.state.first()
        assertFalse(currentState.isLoading)
        assertNull(currentState.itemDetail)
        assertEquals(errorMessage, currentState.error)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `init should throw if productId is missing in SavedStateHandle`() = runTest {
        // No se provee el productId
        val savedStateHandle = SavedStateHandle()

        // Cuando se instancia el ViewModel
        // Debe lanzar IllegalArgumentException
        viewModel = DetailsViewModel(mockGetItemDetailUseCase, savedStateHandle)
    }
}
