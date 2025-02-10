package com.luis.dev.meliapp.features.authentication.presentation.reset

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luis.dev.meliapp.features.authentication.data.repository.ResetPasswordRepositoryResult
import com.luis.dev.meliapp.features.authentication.domain.models.ResetPasswordError
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

    private val _state = MutableStateFlow(ResetPasswordState())
    val state: StateFlow<ResetPasswordState> get() = _state

    fun handleIntent(intent: ResetPasswordIntent) {
        when (intent) {
            is ResetPasswordIntent.EmailChanged -> {
                _state.value = _state.value.copy(email = intent.email, error = null)
            }
            is ResetPasswordIntent.ResetClicked -> {
                resetPassword()
            }
            is ResetPasswordIntent.ClearError -> {
                _state.value = _state.value.copy(error = null)
            }
        }
    }

    private fun resetPassword() {
        viewModelScope.launch {
            val current = _state.value
            if (!isEmailValid(current.email)) {
                _state.value = current.copy(error = ResetPasswordError.InvalidEmail)
                return@launch
            }

            _state.value = current.copy(isLoading = true, error = null)

            val result = resetPasswordUseCase(current.email)
            when (result) {
                is ResetPasswordRepositoryResult.Success -> {
                    _state.value = _state.value.copy(isLoading = false, resetEmailSent = true)
                }
                is ResetPasswordRepositoryResult.Error -> {
                    _state.value = _state.value.copy(isLoading = false, error = ResetPasswordError.FirebaseError(result.message))
                }
                ResetPasswordRepositoryResult.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
            }
        }
    }

    private fun isEmailValid(email: String): Boolean {
        val pattern = Pattern.compile(
            "^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$",
            Pattern.CASE_INSENSITIVE
        )
        return pattern.matcher(email).matches()
    }
}