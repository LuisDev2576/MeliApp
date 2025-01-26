package com.luis.dev.meliapp.features.authentication.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.luis.dev.meliapp.features.authentication.data.model.UserInformation
import com.luis.dev.meliapp.features.authentication.data.repository.RegistrationRepositoryResult
import com.luis.dev.meliapp.features.authentication.data.repository.UserInformationRepository
import com.luis.dev.meliapp.features.authentication.domain.usecases.RegisterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class RegisterViewModel(
    private val registerUseCase: RegisterUseCase,
    private val userInformationRepository: UserInformationRepository
) : ViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> get() = _state

    fun handleIntent(intent: RegisterIntent) {
        when (intent) {
            is RegisterIntent.FullNameChanged -> {
                _state.value = _state.value.copy(fullName = intent.name)
            }
            is RegisterIntent.EmailChanged -> {
                _state.value = _state.value.copy(email = intent.email)
            }
            is RegisterIntent.PasswordChanged -> {
                _state.value = _state.value.copy(password = intent.password)
            }
            is RegisterIntent.ConfirmPasswordChanged -> {
                _state.value = _state.value.copy(confirmPassword = intent.confirmPassword)
            }
            is RegisterIntent.RegisterClicked -> {
                register()
            }
            is RegisterIntent.ClearError -> {
                _state.value = _state.value.copy(errorMessage = null)
            }
        }
    }

    private fun register() {
        viewModelScope.launch {
            val current = _state.value

            // Validaciones locales
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
                    val user: FirebaseUser = result.data
                    // Subir info de usuario
                    val userInfo = UserInformation(
                        uid = user.uid,
                        name = current.fullName,
                        email = current.email
                    )
                    val success = userInformationRepository.uploadUserInformation(user.uid, userInfo)
                    if (success) {
                        _state.value = _state.value.copy(isLoading = false, registeredSuccess = true)
                    } else {
                        _state.value = _state.value.copy(isLoading = false, errorMessage = "Error al guardar datos del usuario")
                    }
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

    private fun isEmailValid(email: String): Boolean {
        val pattern = Pattern.compile("^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$", Pattern.CASE_INSENSITIVE)
        return pattern.matcher(email).matches()
    }
}
