package com.luis.dev.meliapp.features.authentication.domain.models

/**
 * Clase sellada para representar los distintos tipos de errores que pueden ocurrir durante el login.
 */
sealed class LoginError {
    object InvalidEmail : LoginError()
    data class ShortPassword(val minLength: Int = 6) : LoginError()
    data class FirebaseError(val message: String? = null) : LoginError()
}