package com.luis.dev.meliapp.features.authentication.presentation.reset

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luis.dev.meliapp.features.authentication.data.repository.ResetPasswordRepositoryResult
import com.luis.dev.meliapp.features.authentication.domain.usecases.ResetPasswordUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.regex.Pattern

/**
 * ViewModel para gestionar el estado y la lógica del flujo de restablecimiento de contraseña.
 *
 * @param resetPasswordUseCase Caso de uso para manejar la lógica de envío del correo de restablecimiento.
 */
class ResetPasswordViewModel(
    private val resetPasswordUseCase: ResetPasswordUseCase
) : ViewModel() {

    /**
     * Estado interno mutable del flujo de restablecimiento de contraseña.
     */
    private val _state = MutableStateFlow(ResetPasswordState())

    /**
     * Estado público inmutable que representa el estado actual del flujo de restablecimiento de contraseña.
     */
    val state: StateFlow<ResetPasswordState> get() = _state

    /**
     * Maneja las intenciones enviadas desde la vista, actualizando el estado o ejecutando acciones según corresponda.
     *
     * @param intent Intención del usuario, como cambios en el correo electrónico o solicitud de restablecimiento.
     */
    fun handleIntent(intent: ResetPasswordIntent) {
        when (intent) {
            is ResetPasswordIntent.EmailChanged -> {
                _state.value = _state.value.copy(email = intent.email, errorMessage = null)
            }
            is ResetPasswordIntent.ResetClicked -> {
                resetPassword()
            }
            is ResetPasswordIntent.ClearError -> {
                _state.value = _state.value.copy(errorMessage = null)
            }
        }
    }

    /**
     * Realiza la operación de envío del correo de restablecimiento de contraseña,
     * validando el correo electrónico ingresado y llamando al caso de uso correspondiente.
     */
    private fun resetPassword() {
        viewModelScope.launch {
            val current = _state.value
            if (!isEmailValid(current.email)) {
                _state.value = current.copy(errorMessage = "Email inválido")
                return@launch
            }

            _state.value = current.copy(isLoading = true, errorMessage = null)

            val result = resetPasswordUseCase(current.email)
            when (result) {
                is ResetPasswordRepositoryResult.Success -> {
                    _state.value = _state.value.copy(isLoading = false, resetEmailSent = true)
                }
                is ResetPasswordRepositoryResult.Error -> {
                    _state.value = _state.value.copy(isLoading = false, errorMessage = result.message)
                }
                ResetPasswordRepositoryResult.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
            }
        }
    }

    /**
     * Valida si el correo electrónico ingresado cumple con un formato válido.
     *
     * @param email Correo electrónico a validar.
     * @return `true` si el correo es válido, de lo contrario `false`.
     */
    private fun isEmailValid(email: String): Boolean {
        val pattern = Pattern.compile(
            "^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$",
            Pattern.CASE_INSENSITIVE
        )
        return pattern.matcher(email).matches()
    }
}
