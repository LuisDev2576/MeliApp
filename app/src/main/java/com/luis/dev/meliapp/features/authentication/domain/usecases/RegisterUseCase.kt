package com.luis.dev.meliapp.features.authentication.domain.usecases

import com.google.firebase.auth.FirebaseUser
import com.luis.dev.meliapp.features.authentication.data.repository.AuthRepository
import com.luis.dev.meliapp.features.authentication.data.repository.RegistrationRepositoryResult

/**
 * Caso de uso para registrar un nuevo usuario.
 *
 * @property repository Repositorio de autenticación utilizado para registrar un usuario.
 */
class RegisterUseCase(
    private val repository: AuthRepository
) {

    /**
     * Ejecuta el registro de un usuario con el correo y contraseña proporcionados.
     *
     * @param email Correo electrónico del usuario.
     * @param password Contraseña del usuario.
     * @return Un [RegistrationRepositoryResult] que contiene el resultado del registro, ya sea éxito o error.
     */
    suspend operator fun invoke(email: String, password: String): RegistrationRepositoryResult<FirebaseUser> {
        return repository.register(email, password)
    }
}