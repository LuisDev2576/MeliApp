// features/authentication/presentation/register/RegisterIntent.kt

package com.luis.dev.meliapp.features.authentication.presentation.register

sealed class RegisterIntent {
    data class FullNameChanged(val name: String) : RegisterIntent()
    data class EmailChanged(val email: String) : RegisterIntent()
    data class PasswordChanged(val password: String) : RegisterIntent()
    data class ConfirmPasswordChanged(val confirmPassword: String) : RegisterIntent()
    object RegisterClicked : RegisterIntent()
    object ClearError : RegisterIntent()
}