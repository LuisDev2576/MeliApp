package com.luis.dev.meliapp.features.authentication.domain.usecases

import com.google.firebase.auth.FirebaseUser
import com.luis.dev.meliapp.features.authentication.data.repository.AuthRepository
import com.luis.dev.meliapp.features.authentication.data.repository.RegistrationRepositoryResult

class RegisterUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): RegistrationRepositoryResult<FirebaseUser> {
        return repository.register(email, password)
    }
}
