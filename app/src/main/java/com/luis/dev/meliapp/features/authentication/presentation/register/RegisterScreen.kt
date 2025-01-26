package com.luis.dev.meliapp.features.authentication.presentation.register

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.luis.dev.meliapp.R
import com.luis.dev.meliapp.features.authentication.presentation.components.ActionButton
import com.luis.dev.meliapp.features.authentication.presentation.components.ConfirmPasswordTextField
import com.luis.dev.meliapp.features.authentication.presentation.components.EmailTextField
import com.luis.dev.meliapp.features.authentication.presentation.components.NameTextField
import com.luis.dev.meliapp.features.authentication.presentation.components.NavigationTextButton
import com.luis.dev.meliapp.features.authentication.presentation.components.PasswordTextField

@Composable
fun RegisterScreen(
    state: RegisterState,
    onIntent: (RegisterIntent) -> Unit,
    onNavigateToLogin: () -> Unit,
    onRegistrationSuccess: () -> Unit
) {
    BackHandler {
        onNavigateToLogin()
    }

    if (state.registeredSuccess) {
        LaunchedEffect(Unit) {
            onRegistrationSuccess()
        }
    }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Crear Cuenta")

            Spacer(modifier = Modifier.height(20.dp))

            NameTextField(
                name = state.fullName,
                onNameChange = { onIntent(RegisterIntent.FullNameChanged(it)) },
                isError = state.errorMessage?.contains("nombre") == true
            )

            Spacer(modifier = Modifier.height(10.dp))

            EmailTextField(
                email = state.email,
                onEmailChange = { onIntent(RegisterIntent.EmailChanged(it)) },
                isError = state.errorMessage?.contains("Email") == true
            )

            Spacer(modifier = Modifier.height(10.dp))

            PasswordTextField(
                password = state.password,
                onPasswordChange = { onIntent(RegisterIntent.PasswordChanged(it)) },
                isError = state.errorMessage?.contains("contraseña") == true,
                onDone = {},
                clearFocusOnDone = false
            )

            Spacer(modifier = Modifier.height(10.dp))

            ConfirmPasswordTextField(
                password = state.confirmPassword,
                onPasswordChange = { onIntent(RegisterIntent.ConfirmPasswordChanged(it)) },
                isError = state.errorMessage?.contains("Las contraseñas no coinciden") == true,
                onDone = { onIntent(RegisterIntent.RegisterClicked) }
            )

            Spacer(modifier = Modifier.height(20.dp))

            ActionButton(
                text = "Registrarse",
                enabled = !state.isLoading,
                onClick = { onIntent(RegisterIntent.RegisterClicked) }
            )

            if (!state.errorMessage.isNullOrEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = state.errorMessage,
                    color = androidx.compose.material.MaterialTheme.colors.error
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            NavigationTextButton(
                primaryTextId = R.string.already_have_account,
                secondaryTextId = R.string.login_button,
                onClick = { onNavigateToLogin() }
            )
        }
    }
}
