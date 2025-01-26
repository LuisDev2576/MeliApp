// features/authentication/presentation/login/LoginState.kt

package com.luis.dev.meliapp.features.authentication.presentation.login

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val success: Boolean = false // para indicar que el login fue exitoso
)
