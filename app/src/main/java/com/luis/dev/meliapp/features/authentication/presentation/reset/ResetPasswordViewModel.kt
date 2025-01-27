package com.luis.dev.meliapp.features.authentication.presentation.reset

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luis.dev.meliapp.features.authentication.data.repository.ResetPasswordRepositoryResult
import com.luis.dev.meliapp.features.authentication.domain.usecases.ResetPasswordUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class ResetPasswordViewModel(
    private val resetPasswordUseCase: ResetPasswordUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ResetPasswordState())
    val state: StateFlow<ResetPasswordState> get() = _state

    fun handleIntent(intent: ResetPasswordIntent) {
        when (intent) {
            is ResetPasswordIntent.EmailChanged -> {
                _state.value = _state.value.copy(email = intent.email)
                _state.value = _state.value.copy(errorMessage = null)
            }
            is ResetPasswordIntent.ResetClicked -> {
                resetPassword()
            }
            is ResetPasswordIntent.ClearError -> {
                _state.value = _state.value.copy(errorMessage = null)
            }
        }
    }

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

    private fun isEmailValid(email: String): Boolean {
        val pattern = Pattern.compile(
            "^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$",
            Pattern.CASE_INSENSITIVE
        )
        return pattern.matcher(email).matches()
    }
}
