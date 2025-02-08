package com.luis.dev.meliapp.features.authentication.domain.usecases

import com.luis.dev.meliapp.features.authentication.data.repository.AuthRepository
import com.luis.dev.meliapp.features.authentication.data.repository.ResetPasswordRepositoryResult

/**
 * Caso de uso para restablecer la contraseña de un usuario.
 *
 * @property repository Repositorio de autenticación utilizado para gestionar la operación de restablecimiento de contraseña.
 */
class ResetPasswordUseCase(
    private val repository: AuthRepository
) {

    /**
     * Envía un correo electrónico para restablecer la contraseña del usuario.
     *
     * @param email Correo electrónico del usuario al que se le enviará el enlace de restablecimiento.
     * @return Un [ResetPasswordRepositoryResult] que indica el resultado de la operación, ya sea éxito o error.
     */
    suspend operator fun invoke(email: String): ResetPasswordRepositoryResult<Unit> {
        return repository.resetPassword(email)
    }
}