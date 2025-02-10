package com.luis.dev.meliapp.features.authentication.presentation.register

import com.luis.dev.meliapp.features.authentication.domain.models.RegisterError

data class RegisterState(
    val fullName: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val error: RegisterError? = null,
    val registeredSuccess: Boolean = false
)