package com.luis.dev.meliapp.features.authentication.domain.usecases

import com.google.firebase.auth.FirebaseUser
import com.luis.dev.meliapp.features.authentication.data.repository.AuthRepository
import com.luis.dev.meliapp.features.authentication.data.repository.AuthRepositoryResult

/**
 * Caso de uso para realizar el inicio de sesión de un usuario.
 *
 * @property repository Repositorio de autenticación utilizado para interactuar con el sistema de autenticación.
 */
class LoginUseCase(
    private val repository: AuthRepository
) {

    /**
     * Ejecuta el inicio de sesión del usuario con el correo y contraseña proporcionados.
     *
     * @param email Correo electrónico del usuario.
     * @param password Contraseña del usuario.
     * @return Un [AuthRepositoryResult] que contiene el resultado de la operación, ya sea éxito o error.
     */
    suspend operator fun invoke(email: String, password: String): AuthRepositoryResult<FirebaseUser> {
        return repository.login(email, password)
    }
}