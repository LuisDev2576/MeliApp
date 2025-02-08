// features/authentication/presentation/login/LoginIntent.kt

package com.luis.dev.meliapp.features.authentication.presentation.login

sealed class LoginIntent {
    data class EmailChanged(val email: String) : LoginIntent()
    data class PasswordChanged(val password: String) : LoginIntent()
    object LoginClicked : LoginIntent()
    object ClearError : LoginIntent()
}