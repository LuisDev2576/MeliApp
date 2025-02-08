package com.luis.dev.meliapp.features.authentication.data.repository

import com.google.firebase.auth.FirebaseUser

/**
 * Interfaz para definir las operaciones del repositorio de autenticación.
 * Este repositorio actúa como intermediario entre los casos de uso y el data source de autenticación.
 */
interface AuthRepository {

    /**
     * Inicia sesión con el correo electrónico y contraseña proporcionados.
     *
     * @param email Correo electrónico del usuario.
     * @param password Contraseña del usuario.
     * @return Un [AuthRepositoryResult] con el resultado de la operación.
     */
    suspend fun login(email: String, password: String): AuthRepositoryResult<FirebaseUser>

    /**
     * Registra un nuevo usuario con el correo electrónico y contraseña proporcionados.
     *
     * @param email Correo electrónico del usuario.
     * @param password Contraseña del usuario.
     * @return Un [RegistrationRepositoryResult] con el resultado del registro.
     */
    suspend fun register(email: String, password: String): RegistrationRepositoryResult<FirebaseUser>

    /**
     * Envía un correo de restablecimiento de contraseña al usuario.
     *
     * @param email Correo electrónico del usuario.
     * @return Un [ResetPasswordRepositoryResult] indicando el resultado de la operación.
     */
    suspend fun resetPassword(email: String): ResetPasswordRepositoryResult<Unit>

    /**
     * Obtiene el usuario autenticado actualmente.
     *
     * @return Un [FirebaseUser] si hay un usuario autenticado, o `null` si no hay sesión activa.
     */
    fun getCurrentUser(): FirebaseUser?

    /**
     * Cierra la sesión del usuario actual.
     */
    fun logout()
}

/**
 * Clase sellada para manejar los resultados de las operaciones de autenticación.
 */
sealed class AuthRepositoryResult<out T> {
    /**
     * Representa un resultado exitoso con los datos asociados.
     *
     * @param data Datos devueltos por la operación.
     */
    data class Success<out T>(val data: T) : AuthRepositoryResult<T>()

    /**
     * Representa un error ocurrido durante la operación.
     *
     * @param message Mensaje descriptivo del error.
     */
    data class Error(val message: String) : AuthRepositoryResult<Nothing>()

    /**
     * Representa el estado de carga de la operación.
     */
    object Loading : AuthRepositoryResult<Nothing>()
}

/**
 * Clase sellada para manejar los resultados del registro de usuarios.
 */
sealed class RegistrationRepositoryResult<out T> {
    data class Success<out T>(val data: T) : RegistrationRepositoryResult<T>()
    data class Error(val message: String) : RegistrationRepositoryResult<Nothing>()
    object Loading : RegistrationRepositoryResult<Nothing>()
}

/**
 * Clase sellada para manejar los resultados de la operación de restablecimiento de contraseña.
 */
sealed class ResetPasswordRepositoryResult<out T> {
    data class Success<out T>(val data: T) : ResetPasswordRepositoryResult<T>()
    data class Error(val message: String) : ResetPasswordRepositoryResult<Nothing>()
    object Loading : ResetPasswordRepositoryResult<Nothing>()
}