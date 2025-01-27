package com.luis.dev.meliapp.features.authentication.data.repository

import com.google.firebase.auth.FirebaseUser
import com.luis.dev.meliapp.features.authentication.data.datasource.AuthDataSource
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AuthRepositoryImplTest {

    private val mockDataSource = mockk<AuthDataSource>()
    private lateinit var repository: AuthRepositoryImpl

    @Before
    fun setUp() {
        repository = AuthRepositoryImpl(mockDataSource)
    }

    @Test
    fun `login should return success when dataSource returns success`() = runTest {
        // Given
        val email = "test@example.com"
        val password = "123456"
        val mockUser = mockk<FirebaseUser>()
        every { mockUser.uid } returns "FAKE_UID"

        coEvery { mockDataSource.loginUser(email, password) } returns AuthRepositoryResult.Success(mockUser)

        // When
        val result = repository.login(email, password)

        // Then
        assertTrue(result is AuthRepositoryResult.Success)
        assertEquals("FAKE_UID", (result as AuthRepositoryResult.Success).data.uid)

        coVerify(exactly = 1) { mockDataSource.loginUser(email, password) }
    }

    @Test
    fun `login should return error when dataSource returns error`() = runTest {
        // Given
        val email = "test@example.com"
        val password = "wrong"
        coEvery { mockDataSource.loginUser(email, password) } returns AuthRepositoryResult.Error("Invalid credentials")

        // When
        val result = repository.login(email, password)

        // Then
        assertTrue(result is AuthRepositoryResult.Error)
        assertEquals("Invalid credentials", (result as AuthRepositoryResult.Error).message)

        coVerify(exactly = 1) { mockDataSource.loginUser(email, password) }
    }

    @Test
    fun `register should return success when dataSource returns success`() = runTest {
        // Given
        val email = "newuser@example.com"
        val password = "123456"
        val mockUser = mockk<FirebaseUser>()
        coEvery { mockDataSource.registerUser(email, password) } returns RegistrationRepositoryResult.Success(mockUser)

        // When
        val result = repository.register(email, password)

        // Then
        assertTrue(result is RegistrationRepositoryResult.Success)
        coVerify(exactly = 1) { mockDataSource.registerUser(email, password) }
    }

    @Test
    fun `register should return error when dataSource returns error`() = runTest {
        // Given
        val email = "newuser@example.com"
        val password = "weak"
        coEvery { mockDataSource.registerUser(email, password) } returns RegistrationRepositoryResult.Error("Weak password")

        // When
        val result = repository.register(email, password)

        // Then
        assertTrue(result is RegistrationRepositoryResult.Error)
        assertEquals("Weak password", (result as RegistrationRepositoryResult.Error).message)
        coVerify(exactly = 1) { mockDataSource.registerUser(email, password) }
    }

    @Test
    fun `resetPassword should return success when dataSource returns success`() = runTest {
        // Given
        val email = "someone@example.com"
        coEvery { mockDataSource.resetPassword(email) } returns ResetPasswordRepositoryResult.Success(Unit)

        // When
        val result = repository.resetPassword(email)

        // Then
        assertTrue(result is ResetPasswordRepositoryResult.Success)
        coVerify(exactly = 1) { mockDataSource.resetPassword(email) }
    }

    @Test
    fun `resetPassword should return error when dataSource returns error`() = runTest {
        // Given
        val email = "nosuchuser@example.com"
        coEvery { mockDataSource.resetPassword(email) } returns ResetPasswordRepositoryResult.Error("User not found")

        // When
        val result = repository.resetPassword(email)

        // Then
        assertTrue(result is ResetPasswordRepositoryResult.Error)
        assertEquals("User not found", (result as ResetPasswordRepositoryResult.Error).message)
        coVerify(exactly = 1) { mockDataSource.resetPassword(email) }
    }

    @Test
    fun `getCurrentUser should return user from dataSource`() {
        // Given
        val mockUser = mockk<FirebaseUser>()
        every { mockDataSource.currentUser() } returns mockUser

        // When
        val currentUser = repository.getCurrentUser()

        // Then
        assertEquals(mockUser, currentUser)
        verify(exactly = 1) { mockDataSource.currentUser() }
    }

    @Test
    fun `logout should call signOut on dataSource`() {
        // Given
        every { mockDataSource.signOut() } just runs

        // When
        repository.logout()

        // Then
        verify(exactly = 1) { mockDataSource.signOut() }
    }
}
