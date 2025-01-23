package com.luis.dev.meliapp.features.search.domain.usecases

import android.util.Log
import com.luis.dev.meliapp.features.search.data.repository.SearchRepository
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import Attribute
import Installments
import Paging
import SearchResponse
import SearchResultItem
import Shipping
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import kotlinx.coroutines.Dispatchers
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchUseCaseImplTest {

    private val mockRepository = mockk<SearchRepository>()
    private lateinit var useCase: SearchUseCaseImpl

    // Usamos TestDispatcher para controlar las corrutinas
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        // Mockear métodos estáticos de Log
        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
        every { Log.e(any(), any(), any<Throwable>()) } returns 0
        every { Log.e(any(), any()) } returns 0

        // Configurar el Dispatcher principal para los tests
        Dispatchers.setMain(testDispatcher)

        // Instanciar el UseCase con el repositorio mockeado
        useCase = SearchUseCaseImpl(mockRepository)
    }

    @After
    fun tearDown() {
        // Resetear el Dispatcher principal
        Dispatchers.resetMain()

        // Desmockear métodos estáticos de Log
        unmockkStatic(Log::class)
    }

    @Test
    fun `execute calls repository and returns results`() = runTest {
        // Dado que el repositorio retorne una respuesta con 2 ítems
        val query = "iphone"
        val fakeResults = listOf(item1, item2) // Usa los items que proporcionaste
        val fakeResponse = SearchResponse(
            siteId = "MLA",
            query = query,
            paging = Paging(
                total = 2,
                offset = 0,
                limit = 50,
                primaryResults = 2
            ),
            results = fakeResults
        )

        coEvery { mockRepository.searchItems(query) } returns fakeResponse

        // Cuando se llama a execute
        val result = useCase.execute(query)

        // Entonces se verifica que el repositorio haya sido llamado
        coVerify(exactly = 1) { mockRepository.searchItems(query) }

        // Y que el resultado de la función sea la lista de items
        assertEquals(fakeResults, result)
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
                // ... otros atributos
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
                // ... otros atributos
            )
        )
    }
}
