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
    fun `cuando se actualiza el nombre, se refleja en el state y errorMessage se limpia`() = runTest {
        // Dado
        val name = "Luis Developer"

        // Cuando
        viewModel.handleIntent(RegisterIntent.FullNameChanged(name))

        // Entonces
        val state = viewModel.state.first()
        assertEquals(name, state.fullName)
        assertNull(state.errorMessage)
    }

    @Test
    fun `si el usuario da click en Register con nombre vacío, se muestra error`() = runTest {
        // Dado
        viewModel.handleIntent(RegisterIntent.FullNameChanged("")) // Nombre vacío
        viewModel.handleIntent(RegisterIntent.EmailChanged("test@example.com"))
        viewModel.handleIntent(RegisterIntent.PasswordChanged("123456"))
        viewModel.handleIntent(RegisterIntent.ConfirmPasswordChanged("123456"))

        // Cuando
        viewModel.handleIntent(RegisterIntent.RegisterClicked)
        testDispatcher.scheduler.advanceUntilIdle()

        // Entonces
        val state = viewModel.state.first()
        assertEquals("El nombre no puede estar vacío", state.errorMessage)
        assertFalse(state.isLoading)
    }

    @Test
    fun `si el usuario da click en Register con email inválido, se muestra error`() = runTest {
        // Dado
        viewModel.handleIntent(RegisterIntent.FullNameChanged("John Doe"))
        viewModel.handleIntent(RegisterIntent.EmailChanged("testexample.com"))
        viewModel.handleIntent(RegisterIntent.PasswordChanged("123456"))
        viewModel.handleIntent(RegisterIntent.ConfirmPasswordChanged("123456"))

        // Cuando
        viewModel.handleIntent(RegisterIntent.RegisterClicked)
        testDispatcher.scheduler.advanceUntilIdle()

        // Entonces
        val state = viewModel.state.first()
        assertEquals("Email inválido", state.errorMessage)
    }

    @Test
    fun `si el usuario da click en Register con contraseña de menos de 6 caracteres, se muestra error`() = runTest {
        // Dado
        viewModel.handleIntent(RegisterIntent.FullNameChanged("Jane Doe"))
        viewModel.handleIntent(RegisterIntent.EmailChanged("test@example.com"))
        viewModel.handleIntent(RegisterIntent.PasswordChanged("12345")) // 5 caracteres
        viewModel.handleIntent(RegisterIntent.ConfirmPasswordChanged("12345"))

        // Cuando
        viewModel.handleIntent(RegisterIntent.RegisterClicked)
        testDispatcher.scheduler.advanceUntilIdle()

        // Entonces
        val state = viewModel.state.first()
        assertEquals("La contraseña debe tener al menos 6 caracteres", state.errorMessage)
    }

    @Test
    fun `si el usuario da click en Register con contraseñas que no coinciden, se muestra error`() = runTest {
        // Dado
        viewModel.handleIntent(RegisterIntent.FullNameChanged("Jane Doe"))
        viewModel.handleIntent(RegisterIntent.EmailChanged("test@example.com"))
        viewModel.handleIntent(RegisterIntent.PasswordChanged("123456"))
        viewModel.handleIntent(RegisterIntent.ConfirmPasswordChanged("654321"))

        // Cuando
        viewModel.handleIntent(RegisterIntent.RegisterClicked)
        testDispatcher.scheduler.advanceUntilIdle()

        // Entonces
        val state = viewModel.state.first()
        assertEquals("Las contraseñas no coinciden", state.errorMessage)
    }

    @Test
    fun `si el usuario da click en Register con datos válidos y registerUseCase retorna Success, se setea registeredSuccess=true`() =
        runTest {
            // Dado
            val fullName = "Jane Doe"
            val email = "test@example.com"
            val password = "123456"
            val mockUser = mockk<FirebaseUser> {
                every { uid } returns "UID_MOCK"
            }

            // Preparar state con datos válidos
            viewModel.handleIntent(RegisterIntent.FullNameChanged(fullName))
            viewModel.handleIntent(RegisterIntent.EmailChanged(email))
            viewModel.handleIntent(RegisterIntent.PasswordChanged(password))
            viewModel.handleIntent(RegisterIntent.ConfirmPasswordChanged(password))

            // Mock de respuesta exitosa del registerUseCase
            coEvery { mockRegisterUseCase(email, password) } returns RegistrationRepositoryResult.Success(mockUser)

            // Cuando
            viewModel.handleIntent(RegisterIntent.RegisterClicked)
            testDispatcher.scheduler.advanceUntilIdle()

            // Entonces
            val state = viewModel.state.first()
            assertTrue(state.registeredSuccess)
            assertFalse(state.isLoading)
            assertNull(state.errorMessage)

            coVerify(exactly = 1) { mockRegisterUseCase(email, password) }
            // Verificación de UserInformationRepository eliminada
        }

    @Test
    fun `cuando registerUseCase retorna Error, se refleja el mensaje de error en state`() = runTest {
        // Dado
        val fullName = "Jane Doe"
        val email = "test@example.com"
        val password = "123456"

        viewModel.handleIntent(RegisterIntent.FullNameChanged(fullName))
        viewModel.handleIntent(RegisterIntent.EmailChanged(email))
        viewModel.handleIntent(RegisterIntent.PasswordChanged(password))
        viewModel.handleIntent(RegisterIntent.ConfirmPasswordChanged(password))

        coEvery { mockRegisterUseCase(email, password) } returns RegistrationRepositoryResult.Error("Error en registro")

        // Cuando
        viewModel.handleIntent(RegisterIntent.RegisterClicked)
        testDispatcher.scheduler.advanceUntilIdle()

        // Entonces
        val state = viewModel.state.first()
        assertEquals("Error en registro", state.errorMessage)
        assertFalse(state.isLoading)
        assertFalse(state.registeredSuccess)
    }

    @Test
    fun `cuando se envía ClearError, se limpia el error en el state`() = runTest {
        // Dado: Forzamos un error
        viewModel.handleIntent(RegisterIntent.FullNameChanged(""))
        viewModel.handleIntent(RegisterIntent.RegisterClicked)
        testDispatcher.scheduler.advanceUntilIdle()
        assertNotNull(viewModel.state.first().errorMessage)

        // Cuando
        viewModel.handleIntent(RegisterIntent.ClearError)

        // Entonces
        assertNull(viewModel.state.first().errorMessage)
    }
}
