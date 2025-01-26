package com.luis.dev.meliapp.features.details.data.repository

import android.util.Log
import com.luis.dev.meliapp.features.details.data.datasource.DetailsDataSource
import com.luis.dev.meliapp.features.details.data.model.Attribute
import com.luis.dev.meliapp.features.details.data.model.ItemDetailResponse
import com.luis.dev.meliapp.features.details.data.model.Picture
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
class DetailsRepositoryImplTest {

    private val mockDataSource = mockk<DetailsDataSource>()
    private lateinit var repository: DetailsRepositoryImpl

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        mockkStatic(Log::class)
        coEvery { Log.d(any(), any()) } returns 0
        coEvery { Log.e(any(), any()) } returns 0

        Dispatchers.setMain(testDispatcher)
        repository = DetailsRepositoryImpl(mockDataSource)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkStatic(Log::class)
    }

    @Test
    fun `getItemDetail calls dataSource and returns ItemDetailResponse`() = runTest {
        // Dado un itemId
        val itemId = "MLA999"
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

        coEvery { mockDataSource.getItemDetail(itemId) } returns fakeResponse

        // Cuando llamamos al repositorio
        val result = repository.getItemDetail(itemId)

        // Verificamos que se llame al dataSource
        coVerify(exactly = 1) { mockDataSource.getItemDetail(itemId) }
        // Comprobamos que devuelva la misma respuesta
        assertEquals(fakeResponse, result)
    }
}
