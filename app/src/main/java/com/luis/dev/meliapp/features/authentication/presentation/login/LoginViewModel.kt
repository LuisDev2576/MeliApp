package com.luis.dev.meliapp.features.authentication.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luis.dev.meliapp.features.authentication.data.repository.AuthRepositoryResult
import com.luis.dev.meliapp.features.authentication.domain.usecases.LoginUseCase
import com.luis.dev.meliapp.features.authentication.data.repository.UserInformationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val userInformationRepository: UserInformationRepository
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> get() = _state

    fun handleIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.EmailChanged -> {
                _state.value = _state.value.copy(email = intent.email)
            }
            is LoginIntent.PasswordChanged -> {
                _state.value = _state.value.copy(password = intent.password)
            }
            is LoginIntent.LoginClicked -> {
                login()
            }
            is LoginIntent.ClearError -> {
                _state.value = _state.value.copy(errorMessage = null)
            }
        }
    }

    private fun login() {
        viewModelScope.launch {
            val current = _state.value

            // Validaciones locales
            if (!isEmailValid(current.email)) {
                _state.value = current.copy(errorMessage = "Email inválido")
                return@launch
            }
            if (!isPasswordValid(current.password)) {
                _state.value = current.copy(errorMessage = "La contraseña debe tener al menos 6 caracteres")
                return@launch
            }

            // Indicar loading
            _state.value = current.copy(isLoading = true, errorMessage = null)

            val result = loginUseCase(current.email, current.password)
            when (result) {
                is AuthRepositoryResult.Success -> {
                    // Descarga la información extra del usuario, si lo deseas
                    val user = result.data
                    val userInfo = userInformationRepository.downloadUserInformation(user.uid)

                    // userInfo puede ser nulo o no
                    // Si no es nulo, guardarlo, etc.
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

    private fun isEmailValid(email: String): Boolean {
        val pattern = Pattern.compile("^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$", Pattern.CASE_INSENSITIVE)
        return pattern.matcher(email).matches()
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length >= 6
    }
}
