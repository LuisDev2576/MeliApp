package com.luis.dev.meliapp.features.authentication.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luis.dev.meliapp.features.authentication.data.repository.AuthRepositoryResult
import com.luis.dev.meliapp.features.authentication.domain.models.LoginError
import com.luis.dev.meliapp.features.authentication.domain.usecases.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.regex.Pattern

/**
 * ViewModel para gestionar el estado y la lógica del flujo de inicio de sesión.
 *
 * @param loginUseCase Caso de uso para manejar la lógica de inicio de sesión.
 */
class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> get() = _state

    fun handleIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.EmailChanged -> {
                _state.value = _state.value.copy(email = intent.email, error = null)
            }
            is LoginIntent.PasswordChanged -> {
                _state.value = _state.value.copy(password = intent.password, error = null)
            }
            is LoginIntent.LoginClicked -> {
                login()
            }
            is LoginIntent.ClearError -> {
                _state.value = _state.value.copy(error = null)
            }
        }
    }

    private fun login() {
        viewModelScope.launch {
            val current = _state.value

            if (!isEmailValid(current.email)) {
                _state.value = current.copy(error = LoginError.InvalidEmail)
                return@launch
            }
            if (!isPasswordValid(current.password)) {
                _state.value = current.copy(error = LoginError.ShortPassword())
                return@launch
            }

            _state.value = current.copy(isLoading = true, error = null)

            val result = loginUseCase(current.email, current.password)
            when (result) {
                is AuthRepositoryResult.Success -> {
                    _state.value = _state.value.copy(isLoading = false, success = true)
                }
                is AuthRepositoryResult.Error -> {
                    _state.value = _state.value.copy(isLoading = false, error = LoginError.FirebaseError(result.message))
                }
                AuthRepositoryResult.Loading -> {
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

    private fun isPasswordValid(password: String): Boolean {
        return password.length >= 6
    }
}