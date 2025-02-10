package com.luis.dev.meliapp.features.authentication.domain.models

sealed class RegisterError {
    object EmptyName : RegisterError()
    object InvalidEmail : RegisterError()
    data class WeakPassword(val minLength: Int = 6) : RegisterError()
    object PasswordMismatch : RegisterError()
    data class FirebaseError(val message: String? = null) : RegisterError()
}