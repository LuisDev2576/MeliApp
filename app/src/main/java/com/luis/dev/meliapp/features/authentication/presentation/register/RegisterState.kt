// features/authentication/presentation/register/RegisterState.kt

package com.luis.dev.meliapp.features.authentication.presentation.register

data class RegisterState(
    val fullName: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val registeredSuccess: Boolean = false
)
