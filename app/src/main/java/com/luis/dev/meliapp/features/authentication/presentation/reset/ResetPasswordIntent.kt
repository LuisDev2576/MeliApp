// features/authentication/presentation/reset/ResetPasswordIntent.kt

package com.luis.dev.meliapp.features.authentication.presentation.reset

sealed class ResetPasswordIntent {
    data class EmailChanged(val email: String) : ResetPasswordIntent()
    object ResetClicked : ResetPasswordIntent()
    object ClearError : ResetPasswordIntent()
}