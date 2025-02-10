package com.luis.dev.meliapp.features.authentication.presentation.login

import com.google.firebase.auth.FirebaseUser
import com.luis.dev.meliapp.features.authentication.data.repository.AuthRepositoryResult
import com.luis.dev.meliapp.features.authentication.domain.models.LoginError
import com.luis.dev.meliapp.features.authentication.domain.usecases.LoginUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
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
        assertNull(state.error)
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
        assertNull(state.error)
    }

    @Test
    fun `when login is attempted with invalid email, error is set to InvalidEmail`() = runTest {
        // Given
        val invalidEmail = "notanemail"
        viewModel.handleIntent(LoginIntent.EmailChanged(invalidEmail))
        viewModel.handleIntent(LoginIntent.PasswordChanged("123456"))

        // When
        viewModel.handleIntent(LoginIntent.LoginClicked)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = viewModel.state.first()
        // Se espera el error tipificado
        assertEquals(LoginError.InvalidEmail, state.error)
        assertFalse(state.isLoading)
    }

    @Test
    fun `when login is attempted with invalid password, error is set to ShortPassword`() = runTest {
        // Given
        viewModel.handleIntent(LoginIntent.EmailChanged("test@example.com"))
        viewModel.handleIntent(LoginIntent.PasswordChanged("123")) // Menos de 6 caracteres

        // When
        viewModel.handleIntent(LoginIntent.LoginClicked)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = viewModel.state.first()
        // Se espera que el error sea ShortPassword
        assertEquals(LoginError.ShortPassword(), state.error)
        assertFalse(state.isLoading)
    }

    @Test
    fun `when loginUseCase returns Success, state is set to success true`() = runTest {
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
        assertNull(state.error)

        coVerify(exactly = 1) { mockLoginUseCase(email, password) }
    }

    @Test
    fun `when loginUseCase returns Error, error is set to FirebaseError`() = runTest {
        // Given
        val email = "test@example.com"
        val password = "123456"
        val errorMessage = "Usuario o contraseña incorrectos"

        viewModel.handleIntent(LoginIntent.EmailChanged(email))
        viewModel.handleIntent(LoginIntent.PasswordChanged(password))

        coEvery { mockLoginUseCase(email, password) } returns AuthRepositoryResult.Error(errorMessage)

        // When
        viewModel.handleIntent(LoginIntent.LoginClicked)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = viewModel.state.first()
        assertFalse(state.isLoading)
        assertFalse(state.success)
        assertEquals(LoginError.FirebaseError(errorMessage), state.error)
    }

    @Test
    fun `when ClearError is sent, error is cleared in the state`() = runTest {
        // Given: Forzamos un error
        viewModel.handleIntent(LoginIntent.EmailChanged("bademail"))
        viewModel.handleIntent(LoginIntent.LoginClicked)
        testDispatcher.scheduler.advanceUntilIdle()
        assertNotNull(viewModel.state.first().error)

        // When
        viewModel.handleIntent(LoginIntent.ClearError)

        // Then
        assertNull(viewModel.state.first().error)
    }
}