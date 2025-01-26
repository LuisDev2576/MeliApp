package com.luis.dev.meliapp.features.authentication.domain.usecases

import com.luis.dev.meliapp.features.authentication.data.repository.AuthRepository
import com.luis.dev.meliapp.features.authentication.data.repository.ResetPasswordRepositoryResult

class ResetPasswordUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String): ResetPasswordRepositoryResult<Unit> {
        return repository.resetPassword(email)
    }
}
