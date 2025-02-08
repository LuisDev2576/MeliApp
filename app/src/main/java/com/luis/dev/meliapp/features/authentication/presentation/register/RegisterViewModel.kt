package com.luis.dev.meliapp.features.authentication.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luis.dev.meliapp.features.authentication.data.repository.RegistrationRepositoryResult
import com.luis.dev.meliapp.features.authentication.domain.usecases.RegisterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.regex.Pattern

/**
 * ViewModel para gestionar el estado y la lógica del flujo de registro de usuarios.
 *
 * @param registerUseCase Caso de uso para manejar la lógica de registro de usuarios.
 */
class RegisterViewModel(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    /**
     * Estado interno mutable del registro.
     */
    private val _state = MutableStateFlow(RegisterState())

    /**
     * Estado público inmutable que representa el estado actual del registro.
     */
    val state: StateFlow<RegisterState> get() = _state

    /**
     * Maneja las intenciones enviadas desde la vista, actualizando el estado o ejecutando acciones según corresponda.
     *
     * @param intent Intención del usuario, como cambios en los campos de texto o iniciar el registro.
     */
    fun handleIntent(intent: RegisterIntent) {
        when (intent) {
            is RegisterIntent.FullNameChanged -> {
                _state.value = _state.value.copy(fullName = intent.name, errorMessage = null)
            }
            is RegisterIntent.EmailChanged -> {
                _state.value = _state.value.copy(email = intent.email, errorMessage = null)
            }
            is RegisterIntent.PasswordChanged -> {
                _state.value = _state.value.copy(password = intent.password, errorMessage = null)
            }
            is RegisterIntent.ConfirmPasswordChanged -> {
                _state.value = _state.value.copy(confirmPassword = intent.confirmPassword, errorMessage = null)
            }
            is RegisterIntent.RegisterClicked -> {
                register()
            }
            is RegisterIntent.ClearError -> {
                _state.value = _state.value.copy(errorMessage = null)
            }
        }
    }

    /**
     * Realiza la operación de registro, validando los datos ingresados y llamando al caso de uso.
     * Actualiza el estado según el resultado de la operación.
     */
    private fun register() {
        viewModelScope.launch {
            val current = _state.value

            if (current.fullName.isBlank()) {
                _state.value = current.copy(errorMessage = "El nombre no puede estar vacío")
                return@launch
            }
            if (!isEmailValid(current.email)) {
                _state.value = current.copy(errorMessage = "Email inválido")
                return@launch
            }
            if (current.password.isBlank() || current.password.length < 6) {
                _state.value = current.copy(errorMessage = "La contraseña debe tener al menos 6 caracteres")
                return@launch
            }
            if (current.password != current.confirmPassword) {
                _state.value = current.copy(errorMessage = "Las contraseñas no coinciden")
                return@launch
            }

            _state.value = current.copy(isLoading = true, errorMessage = null)

            val result = registerUseCase(current.email, current.password)
            when (result) {
                is RegistrationRepositoryResult.Success -> {
                    _state.value = _state.value.copy(isLoading = false, registeredSuccess = true)
                }
                is RegistrationRepositoryResult.Error -> {
                    _state.value = _state.value.copy(isLoading = false, errorMessage = result.message)
                }
                RegistrationRepositoryResult.Loading -> {
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