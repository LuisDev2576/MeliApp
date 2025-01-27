package com.luis.dev.meliapp.features.authentication.data.repository

import com.google.firebase.auth.FirebaseUser
import com.luis.dev.meliapp.features.authentication.data.datasource.AuthenticationDataSource

/**
 * Implementación de [AuthRepository] que utiliza un [AuthenticationDataSource] para realizar operaciones de autenticación.
 *
 * @property dataSource El data source que maneja la lógica específica de autenticación con Firebase.
 */
class AuthRepositoryImpl(
    private val dataSource: AuthenticationDataSource
) : AuthRepository {

    /**
     * Inicia sesión con el correo electrónico y contraseña proporcionados.
     *
     * @param email Correo electrónico del usuario.
     * @param password Contraseña del usuario.
     * @return Un [AuthRepositoryResult] con el resultado de la operación.
     */
    override suspend fun login(email: String, password: String): AuthRepositoryResult<FirebaseUser> {
        return dataSource.loginUser(email, password)
    }

    /**
     * Registra un nuevo usuario con el correo electrónico y contraseña proporcionados.
     *
     * @param email Correo electrónico del usuario.
     * @param password Contraseña del usuario.
     * @return Un [RegistrationRepositoryResult] con el resultado del registro.
     */
    override suspend fun register(email: String, password: String): RegistrationRepositoryResult<FirebaseUser> {
        return dataSource.registerUser(email, password)
    }

    /**
     * Envía un correo de restablecimiento de contraseña al usuario.
     *
     * @param email Correo electrónico del usuario.
     * @return Un [ResetPasswordRepositoryResult] indicando el resultado de la operación.
     */
    override suspend fun resetPassword(email: String): ResetPasswordRepositoryResult<Unit> {
        return dataSource.resetPassword(email)
    }

    /**
     * Obtiene el usuario autenticado actualmente.
     *
     * @return Un [FirebaseUser] si hay un usuario autenticado, o `null` si no hay sesión activa.
     */
    override fun getCurrentUser(): FirebaseUser? = dataSource.currentUser()

    /**
     * Cierra la sesión del usuario actual.
     */
    override fun logout() = dataSource.signOut()
}
