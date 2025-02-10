package com.luis.dev.meliapp.features.authentication.presentation.login

import com.luis.dev.meliapp.features.authentication.domain.models.LoginError

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: LoginError? = null, // Reemplaza errorMessage
    val success: Boolean = false // para indicar que el login fue exitoso
)