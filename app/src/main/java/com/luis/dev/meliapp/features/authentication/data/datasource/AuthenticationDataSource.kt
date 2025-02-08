package com.luis.dev.meliapp.features.authentication.data.datasource

import com.google.firebase.auth.FirebaseUser
import com.luis.dev.meliapp.features.authentication.data.repository.AuthRepositoryResult
import com.luis.dev.meliapp.features.authentication.data.repository.RegistrationRepositoryResult
import com.luis.dev.meliapp.features.authentication.data.repository.ResetPasswordRepositoryResult

/**
 * Interfaz que define las operaciones de autenticación (login, register, reset, etc.)
 */
interface AuthenticationDataSource {
    suspend fun loginUser(email: String, password: String): AuthRepositoryResult<FirebaseUser>
    suspend fun registerUser(email: String, password: String): RegistrationRepositoryResult<FirebaseUser>
    suspend fun resetPassword(email: String): ResetPasswordRepositoryResult<Unit>
    fun currentUser(): FirebaseUser?
    fun signOut()
}