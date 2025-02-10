package com.luis.dev.meliapp.features.authentication.presentation.reset

import com.luis.dev.meliapp.features.authentication.data.repository.ResetPasswordRepositoryResult
import com.luis.dev.meliapp.features.authentication.domain.models.ResetPasswordError
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
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
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
    fun `when EmailChanged is sent, the state is updated with the new email and error is cleared`() = runTest {
        // Given
        val newEmail = "test@example.com"

        // When
        viewModel.handleIntent(ResetPasswordIntent.EmailChanged(newEmail))

        // Then
        val currentState = viewModel.state.first()
        assertEquals(newEmail, currentState.email)
        assertNull(currentState.error)
    }

    @Test
    fun `when ResetClicked is sent with an invalid email, error is set to InvalidEmail and loading is false`() = runTest {
        // Given an invalid email
        val invalidEmail = "testexample.com"
        viewModel.handleIntent(ResetPasswordIntent.EmailChanged(invalidEmail))

        // When
        viewModel.handleIntent(ResetPasswordIntent.ResetClicked)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val currentState = viewModel.state.first()
        assertEquals(ResetPasswordError.InvalidEmail, currentState.error)
        assertFalse(currentState.isLoading)
    }

    @Test
    fun `when ResetClicked is sent with a valid email and useCase returns Success, resetEmailSent is set to true`() = runTest {
        // Given a valid email
        val validEmail = "test@example.com"
        viewModel.handleIntent(ResetPasswordIntent.EmailChanged(validEmail))

        // Mock successful response
        coEvery { mockResetPasswordUseCase.invoke(validEmail) } returns ResetPasswordRepositoryResult.Success(Unit)

        // When
        viewModel.handleIntent(ResetPasswordIntent.ResetClicked)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val currentState = viewModel.state.first()
        assertTrue(currentState.resetEmailSent)
        assertFalse(currentState.isLoading)
        assertNull(currentState.error)

        // Verify that the use case was called exactly once
        coVerify(exactly = 1) { mockResetPasswordUseCase.invoke(validEmail) }
    }

    @Test
    fun `when ResetClicked is sent with a valid email and useCase returns Error, error is set to FirebaseError`() = runTest {
        // Given a valid email
        val validEmail = "test@example.com"
        viewModel.handleIntent(ResetPasswordIntent.EmailChanged(validEmail))

        // Mock error response
        val errorMsg = "Error al resetear contraseña"
        coEvery { mockResetPasswordUseCase.invoke(validEmail) } returns ResetPasswordRepositoryResult.Error(errorMsg)

        // When
        viewModel.handleIntent(ResetPasswordIntent.ResetClicked)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val currentState = viewModel.state.first()
        assertFalse(currentState.isLoading)
        assertFalse(currentState.resetEmailSent)
        assertEquals(ResetPasswordError.FirebaseError(errorMsg), currentState.error)
    }

    @Test
    fun `when ClearError is sent, error is set to null`() = runTest {
        // Given that an error is set (por ejemplo, con un email inválido)
        viewModel.handleIntent(ResetPasswordIntent.EmailChanged("invalid"))
        viewModel.handleIntent(ResetPasswordIntent.ResetClicked)
        testDispatcher.scheduler.advanceUntilIdle()

        // State with error
        assertNotNull(viewModel.state.first().error)

        // When
        viewModel.handleIntent(ResetPasswordIntent.ClearError)

        // Then
        assertNull(viewModel.state.first().error)
    }
}