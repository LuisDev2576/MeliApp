package com.luis.dev.meliapp.features.authentication.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luis.dev.meliapp.features.authentication.data.repository.AuthRepositoryResult
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

    /**
     * Estado público inmutable que representa el estado actual de la vista.
     */
    val state: StateFlow<LoginState> get() = _state

    /**
     * Maneja las intenciones enviadas desde la vista, actualizando el estado o ejecutando acciones según corresponda.
     *
     * @param intent Intención del usuario, como cambios en los campos de texto o iniciar sesión.
     */
    fun handleIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.EmailChanged -> {
                _state.value = _state.value.copy(email = intent.email, errorMessage = null)
            }
            is LoginIntent.PasswordChanged -> {
                _state.value = _state.value.copy(password = intent.password, errorMessage = null)
            }
            is LoginIntent.LoginClicked -> {
                login()
            }
            is LoginIntent.ClearError -> {
                _state.value = _state.value.copy(errorMessage = null)
            }
        }
    }

    /**
     * Realiza la operación de inicio de sesión, validando los datos ingresados y llamando al caso de uso.
     * Actualiza el estado según el resultado de la operación.
     */
    private fun login() {
        viewModelScope.launch {
            val current = _state.value

            if (!isEmailValid(current.email)) {
                _state.value = current.copy(errorMessage = "Email inválido")
                return@launch
            }
            if (!isPasswordValid(current.password)) {
                _state.value = current.copy(errorMessage = "La contraseña debe tener al menos 6 caracteres")
                return@launch
            }

            _state.value = current.copy(isLoading = true, errorMessage = null)

            val result = loginUseCase(current.email, current.password)
            when (result) {
                is AuthRepositoryResult.Success -> {
                    _state.value = _state.value.copy(isLoading = false, success = true)
                }
                is AuthRepositoryResult.Error -> {
                    _state.value = _state.value.copy(isLoading = false, errorMessage = result.message)
                }
                AuthRepositoryResult.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
            }
        }
    }

    /**
     * Valida si el email ingresado cumple con un formato válido.
     *
     * @param email Correo electrónico a validar.
     * @return `true` si el email es válido, de lo contrario `false`.
     */
    private fun isEmailValid(email: String): Boolean {
        val pattern = Pattern.compile(
            "^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$",
            Pattern.CASE_INSENSITIVE
        )
        return pattern.matcher(email).matches()
    }

    /**
     * Valida si la contraseña cumple con los criterios de longitud mínima.
     *
     * @param password Contraseña a validar.
     * @return `true` si la contraseña es válida, de lo contrario `false`.
     */
    private fun isPasswordValid(password: String): Boolean {
        return password.length >= 6
    }
}
