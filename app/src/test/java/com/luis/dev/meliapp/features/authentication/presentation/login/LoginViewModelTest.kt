package com.luis.dev.meliapp.features.authentication.presentation.login

import com.google.firebase.auth.FirebaseUser
import com.luis.dev.meliapp.features.authentication.data.repository.AuthRepositoryResult
import com.luis.dev.meliapp.features.authentication.domain.usecases.LoginUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val mockLoginUseCase = mockk<LoginUseCase>()
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = LoginViewModel(
            loginUseCase = mockLoginUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `cuando se actualiza email, se refleja en el state y limpia error`() = runTest {
        // Dado
        val email = "test@example.com"

        // Cuando
        viewModel.handleIntent(LoginIntent.EmailChanged(email))

        // Entonces
        val state = viewModel.state.first()
        assertEquals(email, state.email)
        assertNull(state.errorMessage)
    }

    @Test
    fun `cuando se actualiza password, se refleja en el state y limpia error`() = runTest {
        // Dado
        val password = "123456"

        // Cuando
        viewModel.handleIntent(LoginIntent.PasswordChanged(password))

        // Entonces
        val state = viewModel.state.first()
        assertEquals(password, state.password)
        assertNull(state.errorMessage)
    }

    @Test
    fun `cuando se hace login con email inválido, se setea errorMessage`() = runTest {
        // Dado
        val invalidEmail = "notanemail"

        viewModel.handleIntent(LoginIntent.EmailChanged(invalidEmail))
        viewModel.handleIntent(LoginIntent.PasswordChanged("123456"))

        // Cuando
        viewModel.handleIntent(LoginIntent.LoginClicked)
        testDispatcher.scheduler.advanceUntilIdle()

        // Entonces
        val state = viewModel.state.first()
        assertEquals("Email inválido", state.errorMessage)
        assertFalse(state.isLoading)
    }

    @Test
    fun `cuando se hace login con contraseña inválida, se setea errorMessage`() = runTest {
        // Dado
        viewModel.handleIntent(LoginIntent.EmailChanged("test@example.com"))
        viewModel.handleIntent(LoginIntent.PasswordChanged("123")) // menor a 6 chars

        // Cuando
        viewModel.handleIntent(LoginIntent.LoginClicked)
        testDispatcher.scheduler.advanceUntilIdle()

        // Entonces
        val state = viewModel.state.first()
        assertEquals("La contraseña debe tener al menos 6 caracteres", state.errorMessage)
        assertFalse(state.isLoading)
    }

    @Test
    fun `cuando loginUseCase retorna Success, state se setea success=true`() = runTest {
        // Given
        val email = "test@example.com"
        val password = "123456"
        val mockUser = mockk<FirebaseUser> {
            every { uid } returns "UID123" // Mocking the uid property
        }
        viewModel.handleIntent(LoginIntent.EmailChanged(email))
        viewModel.handleIntent(LoginIntent.PasswordChanged(password))

        coEvery { mockLoginUseCase(email, password) } returns AuthRepositoryResult.Success(mockUser)

        // When
        viewModel.handleIntent(LoginIntent.LoginClicked)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = viewModel.state.first()
        assertFalse(state.isLoading)
        assertTrue(state.success)
        assertNull(state.errorMessage)

        coVerify(exactly = 1) { mockLoginUseCase(email, password) }
        // Verificación de UserInformationRepository eliminada
    }

    @Test
    fun `cuando loginUseCase retorna Error, se setea errorMessage`() = runTest {
        // Dado
        val email = "test@example.com"
        val password = "123456"
        val error = "Usuario o contraseña incorrectos"

        viewModel.handleIntent(LoginIntent.EmailChanged(email))
        viewModel.handleIntent(LoginIntent.PasswordChanged(password))

        coEvery { mockLoginUseCase(email, password) } returns AuthRepositoryResult.Error(error)

        // Cuando
        viewModel.handleIntent(LoginIntent.LoginClicked)
        testDispatcher.scheduler.advanceUntilIdle()

        // Entonces
        val state = viewModel.state.first()
        assertFalse(state.isLoading)
        assertFalse(state.success)
        assertEquals(error, state.errorMessage)
    }

    @Test
    fun `cuando se envía ClearError, se limpia el error en el state`() = runTest {
        // Dado un error anterior
        viewModel.handleIntent(LoginIntent.EmailChanged("bademail"))
        viewModel.handleIntent(LoginIntent.LoginClicked)
        testDispatcher.scheduler.advanceUntilIdle()
        assertNotNull(viewModel.state.first().errorMessage)

        // Cuando
        viewModel.handleIntent(LoginIntent.ClearError)

        // Entonces
        assertNull(viewModel.state.first().errorMessage)
    }
}
