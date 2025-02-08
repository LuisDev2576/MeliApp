// features/authentication/presentation/reset/ResetPasswordState.kt

package com.luis.dev.meliapp.features.authentication.presentation.reset

data class ResetPasswordState(
    val email: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val resetEmailSent: Boolean = false
)