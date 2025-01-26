package com.luis.dev.meliapp.core.components.searchTopAppBar

import android.util.Log
import io.mockk.every
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
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchBarViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: SearchBarViewModel

    @Before
    fun setup() {
        // Mockear métodos estáticos de Log para evitar errores en tests
        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
        every { Log.e(any(), any()) } returns 0

        // Configurar el Dispatcher principal
        Dispatchers.setMain(testDispatcher)

        // Instanciar el ViewModel
        viewModel = SearchBarViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkStatic(Log::class)
    }

    @Test
    fun `when UpdateQuery is handled, state query changes`() = runTest {
        // Dado un texto de búsqueda
        val newQuery = "laptop"

        // Cuando se envía la intención
        viewModel.handleIntent(SearchBarIntent.UpdateQuery(newQuery))

        // Entonces la query del state debe reflejar el nuevo valor
        val currentState = viewModel.state.first()
        assertEquals(newQuery, currentState.query)
    }

    @Test
    fun `when ClearQuery is handled, state query is empty`() = runTest {
        // Dado un texto inicial en el state
        val initialQuery = "smartphone"
        viewModel.handleIntent(SearchBarIntent.UpdateQuery(initialQuery))

        // Cuando se envía la intención de limpiar
        viewModel.handleIntent(SearchBarIntent.ClearQuery)

        // Entonces la query debe quedar vacía
        val currentState = viewModel.state.first()
        assertEquals("", currentState.query)
    }
}
