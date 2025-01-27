package com.luis.dev.meliapp.features.authentication.domain.usecases

import com.google.firebase.auth.FirebaseUser
import com.luis.dev.meliapp.features.authentication.data.repository.AuthRepository
import com.luis.dev.meliapp.features.authentication.data.repository.AuthRepositoryResult
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginUseCaseTest {

    private val mockRepository = mockk<AuthRepository>()
    private val useCase = LoginUseCase(mockRepository)

    @Test
    fun `execute should return Success when repository returns Success`() = runTest {
        // Given
        val email = "test@example.com"
        val password = "123456"
        val mockUser = mockk<FirebaseUser>()
        coEvery { mockRepository.login(email, password) } returns AuthRepositoryResult.Success(mockUser)

        // When
        val result = useCase(email, password)

        // Then
        assertTrue(result is AuthRepositoryResult.Success)
        coVerify(exactly = 1) { mockRepository.login(email, password) }
    }

    @Test
    fun `execute should return Error when repository returns Error`() = runTest {
        // Given
        val email = "test@example.com"
        val password = "wrongpassword"
        coEvery { mockRepository.login(email, password) } returns AuthRepositoryResult.Error("Invalid credentials")

        // When
        val result = useCase(email, password)

        // Then
        assertTrue(result is AuthRepositoryResult.Error)
        assertEquals("Invalid credentials", (result as AuthRepositoryResult.Error).message)
        coVerify(exactly = 1) { mockRepository.login(email, password) }
    }
}
