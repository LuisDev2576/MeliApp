package com.luis.dev.meliapp.features.authentication.presentation.reset

import com.luis.dev.meliapp.features.authentication.data.repository.ResetPasswordRepositoryResult
import com.luis.dev.meliapp.features.authentication.domain.usecases.ResetPasswordUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
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
class ResetPasswordViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val mockResetPasswordUseCase = mockk<ResetPasswordUseCase>()
    private lateinit var viewModel: ResetPasswordViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = ResetPasswordViewModel(mockResetPasswordUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `cuando se envía EmailChanged, el state se actualiza con el nuevo email y limpia error`() = runTest {
        // Dado
        val newEmail = "test@example.com"

        // Cuando
        viewModel.handleIntent(ResetPasswordIntent.EmailChanged(newEmail))

        // Entonces
        val currentState = viewModel.state.first()
        assertEquals(newEmail, currentState.email)
        assertNull(currentState.errorMessage)
    }

    @Test
    fun `cuando se envía ResetClicked con email inválido, se muestra mensaje de error`() = runTest {
        // Dado un email inválido
        val invalidEmail = "testexample.com"
        viewModel.handleIntent(ResetPasswordIntent.EmailChanged(invalidEmail))

        // Cuando
        viewModel.handleIntent(ResetPasswordIntent.ResetClicked)
        // Avanzamos corrutinas
        testDispatcher.scheduler.advanceUntilIdle()

        // Entonces
        val currentState = viewModel.state.first()
        assertEquals("Email inválido", currentState.errorMessage)
        assertFalse(currentState.isLoading)
    }

    @Test
    fun `cuando se envía ResetClicked con email válido y useCase retorna Success, se setea resetEmailSent=true`() = runTest {
        // Dado un email válido
        val validEmail = "test@example.com"
        viewModel.handleIntent(ResetPasswordIntent.EmailChanged(validEmail))

        // Mock de la respuesta exitosa
        coEvery { mockResetPasswordUseCase.invoke(validEmail) } returns ResetPasswordRepositoryResult.Success(Unit)

        // Cuando
        viewModel.handleIntent(ResetPasswordIntent.ResetClicked)
        testDispatcher.scheduler.advanceUntilIdle()

        // Entonces
        val currentState = viewModel.state.first()
        assertTrue(currentState.resetEmailSent)
        assertFalse(currentState.isLoading)
        assertNull(currentState.errorMessage)

        // Verificamos que se haya llamado una sola vez
        coVerify(exactly = 1) { mockResetPasswordUseCase.invoke(validEmail) }
    }

    @Test
    fun `cuando se envía ResetClicked con email válido y useCase retorna Error, se setea errorMessage`() = runTest {
        // Dado un email válido
        val validEmail = "test@example.com"
        viewModel.handleIntent(ResetPasswordIntent.EmailChanged(validEmail))

        // Mock de error
        coEvery { mockResetPasswordUseCase.invoke(validEmail) } returns ResetPasswordRepositoryResult.Error("Error al resetear contraseña")

        // Cuando
        viewModel.handleIntent(ResetPasswordIntent.ResetClicked)
        testDispatcher.scheduler.advanceUntilIdle()

        // Entonces
        val currentState = viewModel.state.first()
        assertFalse(currentState.isLoading)
        assertFalse(currentState.resetEmailSent)
        assertEquals("Error al resetear contraseña", currentState.errorMessage)
    }

    @Test
    fun `cuando se envía ClearError, se pone errorMessage en null`() = runTest {
        // Dado que tenemos un error actual
        viewModel.handleIntent(ResetPasswordIntent.EmailChanged("invalid"))
        viewModel.handleIntent(ResetPasswordIntent.ResetClicked)
        testDispatcher.scheduler.advanceUntilIdle()

        // Estado con error
        assertNotNull(viewModel.state.first().errorMessage)

        // Cuando
        viewModel.handleIntent(ResetPasswordIntent.ClearError)

        // Entonces
        assertNull(viewModel.state.first().errorMessage)
    }
}
