package com.luis.dev.meliapp.features.authentication.domain.usecases

import com.google.firebase.auth.FirebaseUser
import com.luis.dev.meliapp.features.authentication.data.repository.AuthRepository
import com.luis.dev.meliapp.features.authentication.data.repository.RegistrationRepositoryResult
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RegisterUseCaseTest {

    private val mockRepository = mockk<AuthRepository>()
    private val useCase = RegisterUseCase(mockRepository)

    @Test
    fun `execute should return Success when repository returns Success`() = runTest {
        // Given
        val email = "test@example.com"
        val password = "123456"
        val mockUser = mockk<FirebaseUser>()
        coEvery { mockRepository.register(email, password) } returns RegistrationRepositoryResult.Success(mockUser)

        // When
        val result = useCase(email, password)

        // Then
        assertTrue(result is RegistrationRepositoryResult.Success)
        coVerify(exactly = 1) { mockRepository.register(email, password) }
    }

    @Test
    fun `execute should return Error when repository returns Error`() = runTest {
        // Given
        val email = "test@example.com"
        val password = "weak"
        coEvery { mockRepository.register(email, password) } returns RegistrationRepositoryResult.Error("Weak password")

        // When
        val result = useCase(email, password)

        // Then
        assertTrue(result is RegistrationRepositoryResult.Error)
        assertEquals("Weak password", (result as RegistrationRepositoryResult.Error).message)
        coVerify(exactly = 1) { mockRepository.register(email, password) }
    }
}
