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
    fun `when email is updated, it is reflected in the state and clears error`() = runTest {
        // Given
        val email = "test@example.com"

        // When
        viewModel.handleIntent(LoginIntent.EmailChanged(email))

        // Then
        val state = viewModel.state.first()
        assertEquals(email, state.email)
        assertNull(state.errorMessage)
    }

    @Test
    fun `when password is updated, it is reflected in the state and clears error`() = runTest {
        // Given
        val password = "123456"

        // When
        viewModel.handleIntent(LoginIntent.PasswordChanged(password))

        // Then
        val state = viewModel.state.first()
        assertEquals(password, state.password)
        assertNull(state.errorMessage)
    }

    @Test
    fun `when login is attempted with invalid email, errorMessage is set`() = runTest {
        // Given
        val invalidEmail = "notanemail"

        viewModel.handleIntent(LoginIntent.EmailChanged(invalidEmail))
        viewModel.handleIntent(LoginIntent.PasswordChanged("123456"))

        // When
        viewModel.handleIntent(LoginIntent.LoginClicked)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = viewModel.state.first()
        assertEquals("Email inválido", state.errorMessage)
        assertFalse(state.isLoading)
    }

    @Test
    fun `when login is attempted with invalid password, errorMessage is set`() = runTest {
        // Given
        viewModel.handleIntent(LoginIntent.EmailChanged("test@example.com"))
        viewModel.handleIntent(LoginIntent.PasswordChanged("123")) // Less than 6 chars

        // When
        viewModel.handleIntent(LoginIntent.LoginClicked)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = viewModel.state.first()
        assertEquals("La contraseña debe tener al menos 6 caracteres", state.errorMessage)
        assertFalse(state.isLoading)
    }

    @Test
    fun `when loginUseCase returns Success, state is set to success=true`() = runTest {
        // Given
        val email = "test@example.com"
        val password = "123456"
        val mockUser = mockk<FirebaseUser> {
            every { uid } returns "UID123"
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
    }

    @Test
    fun `when loginUseCase returns Error, errorMessage is set`() = runTest {
        // Given
        val email = "test@example.com"
        val password = "123456"
        val error = "Usuario o contraseña incorrectos"

        viewModel.handleIntent(LoginIntent.EmailChanged(email))
        viewModel.handleIntent(LoginIntent.PasswordChanged(password))

        coEvery { mockLoginUseCase(email, password) } returns AuthRepositoryResult.Error(error)

        // When
        viewModel.handleIntent(LoginIntent.LoginClicked)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = viewModel.state.first()
        assertFalse(state.isLoading)
        assertFalse(state.success)
        assertEquals(error, state.errorMessage)
    }

    @Test
    fun `when ClearError is sent, error is cleared in the state`() = runTest {
        // Given a previous error
        viewModel.handleIntent(LoginIntent.EmailChanged("bademail"))
        viewModel.handleIntent(LoginIntent.LoginClicked)
        testDispatcher.scheduler.advanceUntilIdle()
        assertNotNull(viewModel.state.first().errorMessage)

        // When
        viewModel.handleIntent(LoginIntent.ClearError)

        // Then
        assertNull(viewModel.state.first().errorMessage)
    }
}
