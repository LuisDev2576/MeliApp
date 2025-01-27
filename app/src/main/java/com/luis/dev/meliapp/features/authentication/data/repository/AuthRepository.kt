package com.luis.dev.meliapp.features.authentication.data.repository

import com.google.firebase.auth.FirebaseUser

/**
 * Interfaz para el repositorio de autenticación,
 * que se apoya en un dataSource (AuthDataSource).
 */
interface AuthRepository {
    suspend fun login(email: String, password: String): AuthRepositoryResult<FirebaseUser>
    suspend fun register(email: String, password: String): RegistrationRepositoryResult<FirebaseUser>
    suspend fun resetPassword(email: String): ResetPasswordRepositoryResult<Unit>
    fun getCurrentUser(): FirebaseUser?
    fun logout()
}

sealed class AuthRepositoryResult<out T> {
    data class Success<out T>(val data: T) : AuthRepositoryResult<T>()
    data class Error(val message: String) : AuthRepositoryResult<Nothing>()
    object Loading : AuthRepositoryResult<Nothing>()
}

sealed class RegistrationRepositoryResult<out T> {
    data class Success<out T>(val data: T) : RegistrationRepositoryResult<T>()
    data class Error(val message: String) : RegistrationRepositoryResult<Nothing>()
    object Loading : RegistrationRepositoryResult<Nothing>()
}

sealed class ResetPasswordRepositoryResult<out T> {
    data class Success<out T>(val data: T) : ResetPasswordRepositoryResult<T>()
    data class Error(val message: String) : ResetPasswordRepositoryResult<Nothing>()
    object Loading : ResetPasswordRepositoryResult<Nothing>()
}
