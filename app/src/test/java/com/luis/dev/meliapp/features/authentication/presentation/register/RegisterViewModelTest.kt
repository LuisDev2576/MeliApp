package com.luis.dev.meliapp.features.authentication.presentation.register

import com.google.firebase.auth.FirebaseUser
import com.luis.dev.meliapp.features.authentication.data.repository.RegistrationRepositoryResult
import com.luis.dev.meliapp.features.authentication.domain.models.RegisterError
import com.luis.dev.meliapp.features.authentication.domain.usecases.RegisterUseCase
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
class RegisterViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val mockRegisterUseCase = mockk<RegisterUseCase>()
    private lateinit var viewModel: RegisterViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = RegisterViewModel(
            registerUseCase = mockRegisterUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when name is updated, it is reflected in the state and error is cleared`() = runTest {
        // Given
        val name = "Luis Developer"

        // When
        viewModel.handleIntent(RegisterIntent.FullNameChanged(name))

        // Then
        val state = viewModel.state.first()
        assertEquals(name, state.fullName)
        assertNull(state.error)
    }

    @Test
    fun `when Register is clicked with an empty name, error is set to EmptyName`() = runTest {
        // Given
        viewModel.handleIntent(RegisterIntent.FullNameChanged("")) // Nombre vacío
        viewModel.handleIntent(RegisterIntent.EmailChanged("test@example.com"))
        viewModel.handleIntent(RegisterIntent.PasswordChanged("123456"))
        viewModel.handleIntent(RegisterIntent.ConfirmPasswordChanged("123456"))

        // When
        viewModel.handleIntent(RegisterIntent.RegisterClicked)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = viewModel.state.first()
        assertEquals(RegisterError.EmptyName, state.error)
        assertFalse(state.isLoading)
    }

    @Test
    fun `when Register is clicked with an invalid email, error is set to InvalidEmail`() = runTest {
        // Given
        viewModel.handleIntent(RegisterIntent.FullNameChanged("John Doe"))
        viewModel.handleIntent(RegisterIntent.EmailChanged("testexample.com"))
        viewModel.handleIntent(RegisterIntent.PasswordChanged("123456"))
        viewModel.handleIntent(RegisterIntent.ConfirmPasswordChanged("123456"))

        // When
        viewModel.handleIntent(RegisterIntent.RegisterClicked)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = viewModel.state.first()
        assertEquals(RegisterError.InvalidEmail, state.error)
    }

    @Test
    fun `when Register is clicked with a password less than 6 characters, error is set to WeakPassword`() = runTest {
        // Given
        viewModel.handleIntent(RegisterIntent.FullNameChanged("Jane Doe"))
        viewModel.handleIntent(RegisterIntent.EmailChanged("test@example.com"))
        viewModel.handleIntent(RegisterIntent.PasswordChanged("12345")) // 5 caracteres
        viewModel.handleIntent(RegisterIntent.ConfirmPasswordChanged("12345"))

        // When
        viewModel.handleIntent(RegisterIntent.RegisterClicked)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = viewModel.state.first()
        assertEquals(RegisterError.WeakPassword(), state.error)
    }

    @Test
    fun `when Register is clicked with non-matching passwords, error is set to PasswordMismatch`() = runTest {
        // Given
        viewModel.handleIntent(RegisterIntent.FullNameChanged("Jane Doe"))
        viewModel.handleIntent(RegisterIntent.EmailChanged("test@example.com"))
        viewModel.handleIntent(RegisterIntent.PasswordChanged("123456"))
        viewModel.handleIntent(RegisterIntent.ConfirmPasswordChanged("654321"))

        // When
        viewModel.handleIntent(RegisterIntent.RegisterClicked)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = viewModel.state.first()
        assertEquals(RegisterError.PasswordMismatch, state.error)
    }

    @Test
    fun `when Register is clicked with valid data and registerUseCase returns Success, registeredSuccess is set to true`() =
        runTest {
            // Given
            val fullName = "Jane Doe"
            val email = "test@example.com"
            val password = "123456"
            val mockUser = mockk<FirebaseUser> {
                every { uid } returns "UID_MOCK"
            }

            // Configuramos un estado válido
            viewModel.handleIntent(RegisterIntent.FullNameChanged(fullName))
            viewModel.handleIntent(RegisterIntent.EmailChanged(email))
            viewModel.handleIntent(RegisterIntent.PasswordChanged(password))
            viewModel.handleIntent(RegisterIntent.ConfirmPasswordChanged(password))

            // Mock de respuesta exitosa de registerUseCase
            coEvery { mockRegisterUseCase(email, password) } returns RegistrationRepositoryResult.Success(mockUser)

            // When
            viewModel.handleIntent(RegisterIntent.RegisterClicked)
            testDispatcher.scheduler.advanceUntilIdle()

            // Then
            val state = viewModel.state.first()
            assertTrue(state.registeredSuccess)
            assertFalse(state.isLoading)
            assertNull(state.error)

            coVerify(exactly = 1) { mockRegisterUseCase(email, password) }
        }

    @Test
    fun `when registerUseCase returns Error, error is set to FirebaseError`() = runTest {
        // Given
        val fullName = "Jane Doe"
        val email = "test@example.com"
        val password = "123456"
        val errorMsg = "Error en registro"

        viewModel.handleIntent(RegisterIntent.FullNameChanged(fullName))
        viewModel.handleIntent(RegisterIntent.EmailChanged(email))
        viewModel.handleIntent(RegisterIntent.PasswordChanged(password))
        viewModel.handleIntent(RegisterIntent.ConfirmPasswordChanged(password))

        coEvery { mockRegisterUseCase(email, password) } returns RegistrationRepositoryResult.Error(errorMsg)

        // When
        viewModel.handleIntent(RegisterIntent.RegisterClicked)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = viewModel.state.first()
        assertEquals(RegisterError.FirebaseError(errorMsg), state.error)
        assertFalse(state.isLoading)
        assertFalse(state.registeredSuccess)
    }

    @Test
    fun `when ClearError is sent, error is cleared in the state`() = runTest {
        // Given: Forzamos un error
        viewModel.handleIntent(RegisterIntent.FullNameChanged(""))
        viewModel.handleIntent(RegisterIntent.RegisterClicked)
        testDispatcher.scheduler.advanceUntilIdle()
        assertNotNull(viewModel.state.first().error)

        // When
        viewModel.handleIntent(RegisterIntent.ClearError)

        // Then
        assertNull(viewModel.state.first().error)
    }
}