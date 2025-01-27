package com.luis.dev.meliapp.features.authentication.domain.usecases

import com.luis.dev.meliapp.features.authentication.data.repository.AuthRepository
import com.luis.dev.meliapp.features.authentication.data.repository.ResetPasswordRepositoryResult
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ResetPasswordUseCaseTest {

    private val mockRepository = mockk<AuthRepository>()
    private val useCase = ResetPasswordUseCase(mockRepository)

    @Test
    fun `execute should return Success when repository returns Success`() = runTest {
        // Given
        val email = "test@example.com"
        coEvery { mockRepository.resetPassword(email) } returns ResetPasswordRepositoryResult.Success(Unit)

        // When
        val result = useCase(email)

        // Then
        assertTrue(result is ResetPasswordRepositoryResult.Success)
        coVerify(exactly = 1) { mockRepository.resetPassword(email) }
    }

    @Test
    fun `execute should return Error when repository returns Error`() = runTest {
        // Given
        val email = "unknown@example.com"
        coEvery { mockRepository.resetPassword(email) } returns ResetPasswordRepositoryResult.Error("User not found")

        // When
        val result = useCase(email)

        // Then
        assertTrue(result is ResetPasswordRepositoryResult.Error)
        assertEquals("User not found", (result as ResetPasswordRepositoryResult.Error).message)
        coVerify(exactly = 1) { mockRepository.resetPassword(email) }
    }
}
