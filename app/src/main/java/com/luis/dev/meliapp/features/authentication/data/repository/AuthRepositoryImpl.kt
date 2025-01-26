package com.luis.dev.meliapp.features.authentication.data.repository

import com.google.firebase.auth.FirebaseUser
import com.luis.dev.meliapp.features.authentication.data.datasource.AuthDataSource

/**
 * Implementación de AuthRepository que delega en un AuthDataSource.
 */
class AuthRepositoryImpl(
    private val dataSource: AuthDataSource
) : AuthRepository {

    override suspend fun login(email: String, password: String): AuthRepositoryResult<FirebaseUser> {
        return dataSource.loginUser(email, password)
    }

    override suspend fun register(email: String, password: String): RegistrationRepositoryResult<FirebaseUser> {
        return dataSource.registerUser(email, password)
    }

    override suspend fun resetPassword(email: String): ResetPasswordRepositoryResult<Unit> {
        return dataSource.resetPassword(email)
    }

    override fun getCurrentUser(): FirebaseUser? = dataSource.currentUser()

    override fun logout() = dataSource.signOut()
}
