package com.luis.dev.meliapp.features.authentication.presentation.reset

import com.luis.dev.meliapp.features.authentication.domain.models.ResetPasswordError

data class ResetPasswordState(
    val email: String = "",
    val isLoading: Boolean = false,
    val error: ResetPasswordError? = null, // Reemplaza errorMessage
    val resetEmailSent: Boolean = false
)