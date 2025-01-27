package com.luis.dev.meliapp.features.authentication.presentation.register

import com.google.firebase.auth.FirebaseUser
import com.luis.dev.meliapp.features.authentication.data.repository.RegistrationRepositoryResult
import com.luis.dev.meliapp.features.authentication.domain.usecases.RegisterUseCase
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
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
    fun `when name is updated, it is reflected in the state and errorMessage is cleared`() = runTest {
        // Given
        val name = "Luis Developer"

        // When
        viewModel.handleIntent(RegisterIntent.FullNameChanged(name))

        // Then
        val state = viewModel.state.first()
        assertEquals(name, state.fullName)
        assertNull(state.errorMessage)
    }

    @Test
    fun `when Register is clicked with an empty name, an error is shown`() = runTest {
        // Given
        viewModel.handleIntent(RegisterIntent.FullNameChanged("")) // Empty name
        viewModel.handleIntent(RegisterIntent.EmailChanged("test@example.com"))
        viewModel.handleIntent(RegisterIntent.PasswordChanged("123456"))
        viewModel.handleIntent(RegisterIntent.ConfirmPasswordChanged("123456"))

        // When
        viewModel.handleIntent(RegisterIntent.RegisterClicked)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = viewModel.state.first()
        assertEquals("El nombre no puede estar vacío", state.errorMessage)
        assertFalse(state.isLoading)
    }

    @Test
    fun `when Register is clicked with an invalid email, an error is shown`() = runTest {
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
        assertEquals("Email inválido", state.errorMessage)
    }

    @Test
    fun `when Register is clicked with a password less than 6 characters, an error is shown`() = runTest {
        // Given
        viewModel.handleIntent(RegisterIntent.FullNameChanged("Jane Doe"))
        viewModel.handleIntent(RegisterIntent.EmailChanged("test@example.com"))
        viewModel.handleIntent(RegisterIntent.PasswordChanged("12345")) // 5 characters
        viewModel.handleIntent(RegisterIntent.ConfirmPasswordChanged("12345"))

        // When
        viewModel.handleIntent(RegisterIntent.RegisterClicked)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = viewModel.state.first()
        assertEquals("La contraseña debe tener al menos 6 caracteres", state.errorMessage)
    }

    @Test
    fun `when Register is clicked with non-matching passwords, an error is shown`() = runTest {
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
        assertEquals("Las contraseñas no coinciden", state.errorMessage)
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

            // Set up valid state
            viewModel.handleIntent(RegisterIntent.FullNameChanged(fullName))
            viewModel.handleIntent(RegisterIntent.EmailChanged(email))
            viewModel.handleIntent(RegisterIntent.PasswordChanged(password))
            viewModel.handleIntent(RegisterIntent.ConfirmPasswordChanged(password))

            // Mock successful response from registerUseCase
            coEvery { mockRegisterUseCase(email, password) } returns RegistrationRepositoryResult.Success(mockUser)

            // When
            viewModel.handleIntent(RegisterIntent.RegisterClicked)
            testDispatcher.scheduler.advanceUntilIdle()

            // Then
            val state = viewModel.state.first()
            assertTrue(state.registeredSuccess)
            assertFalse(state.isLoading)
            assertNull(state.errorMessage)

            coVerify(exactly = 1) { mockRegisterUseCase(email, password) }
        }

    @Test
    fun `when registerUseCase returns Error, the error message is reflected in the state`() = runTest {
        // Given
        val fullName = "Jane Doe"
        val email = "test@example.com"
        val password = "123456"

        viewModel.handleIntent(RegisterIntent.FullNameChanged(fullName))
        viewModel.handleIntent(RegisterIntent.EmailChanged(email))
        viewModel.handleIntent(RegisterIntent.PasswordChanged(password))
        viewModel.handleIntent(RegisterIntent.ConfirmPasswordChanged(password))

        coEvery { mockRegisterUseCase(email, password) } returns RegistrationRepositoryResult.Error("Error en registro")

        // When
        viewModel.handleIntent(RegisterIntent.RegisterClicked)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = viewModel.state.first()
        assertEquals("Error en registro", state.errorMessage)
        assertFalse(state.isLoading)
        assertFalse(state.registeredSuccess)
    }

    @Test
    fun `when ClearError is sent, the error is cleared in the state`() = runTest {
        // Given: Force an error
        viewModel.handleIntent(RegisterIntent.FullNameChanged(""))
        viewModel.handleIntent(RegisterIntent.RegisterClicked)
        testDispatcher.scheduler.advanceUntilIdle()
        assertNotNull(viewModel.state.first().errorMessage)

        // When
        viewModel.handleIntent(RegisterIntent.ClearError)

        // Then
        assertNull(viewModel.state.first().errorMessage)
    }
}
