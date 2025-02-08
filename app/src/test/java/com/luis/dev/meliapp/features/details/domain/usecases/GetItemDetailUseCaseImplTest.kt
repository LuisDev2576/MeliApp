package com.luis.dev.meliapp.features.details.domain.usecases

import android.util.Log
import com.luis.dev.meliapp.features.details.data.model.Attribute
import com.luis.dev.meliapp.features.details.data.model.ItemDetailResponse
import com.luis.dev.meliapp.features.details.data.model.Picture
import com.luis.dev.meliapp.features.details.data.repository.DetailsRepository
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
class GetItemDetailUseCaseImplTest {

    private val mockRepository = mockk<DetailsRepository>()
    private lateinit var useCase: GetItemDetailUseCaseImpl

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        mockkStatic(Log::class)
        coEvery { Log.d(any(), any()) } returns 0
        coEvery { Log.e(any(), any()) } returns 0

        Dispatchers.setMain(testDispatcher)
        useCase = GetItemDetailUseCaseImpl(mockRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkStatic(Log::class)
    }

    @Test
    fun `execute calls repository and returns ItemDetailResponse`() = runTest {
        // Dado un itemId
        val itemId = "MLA1111"
        val fakeResponse = ItemDetailResponse(
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

        // Mockear respuesta del repositorio
        coEvery { mockRepository.getItemDetail(itemId) } returns fakeResponse

        // Cuando se ejecuta el useCase
        val result = useCase.execute(itemId)

        // Entonces se verifica la llamada al repositorio
        coVerify(exactly = 1) { mockRepository.getItemDetail(itemId) }

        // Y se compara el resultado
        assertEquals(fakeResponse, result)
    }
}