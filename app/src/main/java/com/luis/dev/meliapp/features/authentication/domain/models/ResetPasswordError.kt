package com.luis.dev.meliapp.features.authentication.domain.models

/**
 * Clase sellada para representar los distintos errores que pueden ocurrir durante el restablecimiento de contraseña.
 */
sealed class ResetPasswordError {
    object InvalidEmail : ResetPasswordError()
    data class FirebaseError(val message: String? = null) : ResetPasswordError()
}