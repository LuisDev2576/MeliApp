package com.luis.dev.meliapp.features.search.presentation

import Attribute
import Installments
import SearchResultItem
import Shipping
import android.util.Log
import com.luis.dev.meliapp.features.search.domain.usecases.SearchUseCase
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {

    // Mock del UseCase
    private val mockSearchUseCase = mockk<SearchUseCase>()

    // TestDispatcher para controlar las corrutinas
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: SearchViewModel

    @Before
    fun setup() {
        // Mockear métodos estáticos de Log
        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
        every { Log.e(any(), any(), any<Throwable>()) } returns 0
        every { Log.e(any(), any()) } returns 0

        // Configurar el Dispatcher principal para los tests
        Dispatchers.setMain(testDispatcher)

        // Instanciar el ViewModel con el UseCase mockeado
        viewModel = SearchViewModel(searchUseCase = mockSearchUseCase)
    }

    @After
    fun tearDown() {
        // Resetear el Dispatcher principal
        Dispatchers.resetMain()

        // Desmockear métodos estáticos de Log
        unmockkStatic(Log::class)
    }

    @Test
    fun `when UpdateQuery is handled, state query changes`() = runTest {
        // Dado un texto de búsqueda "iphone"
        val newQuery = "iphone"

        // Cuando enviamos la intención de actualizar la query
        viewModel.handleIntent(SearchIntent.UpdateQuery(newQuery))

        // Entonces el estado interno del ViewModel debe reflejar la nueva query
        val currentState = viewModel.state.first()
        assertEquals(newQuery, currentState.query)
        assertEquals(null, currentState.error)
    }

    @Test
    fun `when PerformSearch is handled and search is successful, results are updated`() = runTest {
        // Dado que mockSearchUseCase retorne una lista de resultados
        val fakeResults = listOf(item1, item2) // Asegúrate de que item1 y item2 estén completamente definidos
        coEvery { mockSearchUseCase.execute(any()) } returns fakeResults

        // Setear una query previa en el state
        val query = "macbook"
        viewModel.handleIntent(SearchIntent.UpdateQuery(query))

        // Enviar la intención de buscar
        viewModel.handleIntent(SearchIntent.PerformSearch)

        // Avanzar el dispatcher para ejecutar las corrutinas pendientes
        testDispatcher.scheduler.advanceUntilIdle()

        // Verificar que se haya llamado al UseCase con la query correcta
        coVerify { mockSearchUseCase.execute(query) }

        // Chequear el estado final del ViewModel
        val currentState = viewModel.state.first()
        assertEquals(false, currentState.isLoading)
        assertEquals(fakeResults, currentState.results)
        assertEquals(null, currentState.error)
    }

    @Test
    fun `when PerformSearch is handled and search throws exception, error is updated`() = runTest {
        // Dado que mockSearchUseCase lance una excepción
        val errorMessage = "Network error"
        coEvery { mockSearchUseCase.execute(any()) } throws Exception(errorMessage)

        // Setear una query previa en el state
        val query = "iphone"
        viewModel.handleIntent(SearchIntent.UpdateQuery(query))

        // Enviar la intención de buscar
        viewModel.handleIntent(SearchIntent.PerformSearch)

        // Avanzar el dispatcher para ejecutar las corrutinas pendientes
        testDispatcher.scheduler.advanceUntilIdle()

        // Chequear el estado final del ViewModel
        val currentState = viewModel.state.first()
        assertEquals(false, currentState.isLoading)
        assertEquals(emptyList<SearchResultItem>(), currentState.results)
        assertEquals(errorMessage, currentState.error)
    }

    companion object {
        val item1 = SearchResultItem(
            id = "MLA1449414435",
            title = "Apple Macbook Air (13 Pulgadas, 2020, Chip M1, 256 Gb De Ssd, 8 Gb De Ram) - Oro",
            price = 1399999.0,
            thumbnail = "http://http2.mlstatic.com/D_604544-MLU75179296686_032024-I.jpg",
            permalink = "https://www.mercadolibre.com.ar/apple-macbook-air-13-pulgadas-2020-chip-m1-256-gb-de-ssd-8-gb-de-ram-oro/p/MLA17828522#wid=MLA1449414435&sid=unknown",
            officialStoreName = null,
            originalPrice = null,
            shipping = Shipping(
                freeShipping = true
            ),
            installments = Installments(
                quantity = 6,
                amount = 314673.11,
                rate = 34.86,
                currencyId = "ARS"
            ),
            attributes = listOf(
                Attribute(id = "BRAND", name = "Marca", valueName = "Apple"),
                Attribute(id = "COLOR", name = "Color", valueName = "Oro"),
                Attribute(id = "DETAILED_MODEL", name = "Modelo detallado", valueName = "MGN93FN/A"),
                Attribute(id = "GTIN", name = "Código universal de producto", valueName = "194252049013"),
                Attribute(id = "ITEM_CONDITION", name = "Condición del ítem", valueName = "Nuevo"),
                Attribute(id = "LINE", name = "Línea", valueName = "MacBook Air"),
                Attribute(id = "MAIN_COLOR", name = "Color principal", valueName = "Dorado"),
                Attribute(id = "MODEL", name = "Modelo", valueName = "A2337"),
                Attribute(id = "PROCESSOR_BRAND", name = "Marca del procesador", valueName = "Apple"),
                Attribute(id = "PROCESSOR_MODEL", name = "Modelo del procesador", valueName = "M1"),
                Attribute(id = "WEIGHT", name = "Peso", valueName = "1.29 kg")
            )
        )

        val item2 = SearchResultItem(
            id = "MLA1454455585",
            title = "Apple iPhone 11 (64 Gb) - Negro",
            price = 1199999.0,
            thumbnail = "http://http2.mlstatic.com/D_656548-MLA46114829749_052021-I.jpg",
            permalink = "https://www.mercadolibre.com.ar/apple-iphone-11-64-gb-negro/p/MLA15149561#wid=MLA1454455585&sid=unknown",
            officialStoreName = "Mercado Libre",
            originalPrice = 1386668.11,
            shipping = Shipping(
                freeShipping = true
            ),
            installments = Installments(
                quantity = 18,
                amount = 66666.61,
                rate = 0.0,
                currencyId = "ARS"
            ),
            attributes = listOf(
                Attribute(id = "BRAND", name = "Marca", valueName = "Apple"),
                Attribute(id = "COLOR", name = "Color", valueName = "Negro"),
                Attribute(id = "DETAILED_MODEL", name = "Modelo detallado", valueName = "Iphone 11"),
                Attribute(id = "GPU_MODEL", name = "Modelo de GPU", valueName = "Apple GPU MP4"),
                Attribute(id = "GTIN", name = "Código universal de producto", valueName = "194252097236,194252097212"),
                Attribute(id = "ITEM_CONDITION", name = "Condición del ítem", valueName = "Nuevo"),
                Attribute(id = "LINE", name = "Línea", valueName = "iPhone 11"),
                Attribute(id = "MAIN_COLOR", name = "Color principal", valueName = "Negro"),
                Attribute(id = "MODEL", name = "Modelo", valueName = "iPhone 11"),
                Attribute(id = "PACKAGE_LENGTH", name = "Largo del paquete", valueName = "16.8 cm"),
                Attribute(id = "PACKAGE_WEIGHT", name = "Peso del paquete", valueName = "400 g"),
                Attribute(id = "PROCESSOR_MODEL", name = "Modelo del procesador", valueName = "Apple A13 Bionic"),
                Attribute(id = "WEIGHT", name = "Peso", valueName = "194 g")
            )
        )
    }
}
