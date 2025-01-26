package com.luis.dev.meliapp.features.authentication.domain.usecases

import com.google.firebase.auth.FirebaseUser
import com.luis.dev.meliapp.features.authentication.data.repository.AuthRepository
import com.luis.dev.meliapp.features.authentication.data.repository.AuthRepositoryResult

class LoginUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): AuthRepositoryResult<FirebaseUser> {
        return repository.login(email, password)
    }
}
