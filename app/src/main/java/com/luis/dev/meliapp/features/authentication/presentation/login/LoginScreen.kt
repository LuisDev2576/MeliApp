package com.luis.dev.meliapp.features.authentication.presentation.login

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
import com.luis.dev.meliapp.features.authentication.presentation.components.EmailTextField
import com.luis.dev.meliapp.features.authentication.presentation.components.NavToRestartPasswordButton
import com.luis.dev.meliapp.features.authentication.presentation.components.NavigationTextButton
import com.luis.dev.meliapp.features.authentication.presentation.components.PasswordTextField

@Composable
fun LoginScreen(
    loginState: LoginState,
    onIntent: (LoginIntent) -> Unit,
    onNavigateToRegister: () -> Unit,
    onNavigateToReset: () -> Unit,
    onNavigateBack: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    BackHandler {
        // Controla el back si lo deseas
        onNavigateBack()
    }

    // Si el login tuvo éxito, podrías navegar
    if (loginState.success) {
        LaunchedEffect(Unit) {
            onLoginSuccess() // o a otra ruta
        }
    }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Iniciar Sesión")

            Spacer(modifier = Modifier.height(20.dp))

            EmailTextField(
                email = loginState.email,
                onEmailChange = { onIntent(LoginIntent.EmailChanged(it)) },
                isError = loginState.errorMessage?.contains("Email") == true
            )

            Spacer(modifier = Modifier.height(10.dp))

            PasswordTextField(
                password = loginState.password,
                onPasswordChange = { onIntent(LoginIntent.PasswordChanged(it)) },
                isError = loginState.errorMessage?.contains("contraseña") == true,
                onDone = { onIntent(LoginIntent.LoginClicked) },
                clearFocusOnDone = true
            )

            NavToRestartPasswordButton(
                onClick = { onNavigateToReset() }
            )

            Spacer(modifier = Modifier.height(10.dp))

            ActionButton(
                text = "Iniciar Sesión",
                enabled = !loginState.isLoading,
                onClick = { onIntent(LoginIntent.LoginClicked) }
            )

            if (!loginState.errorMessage.isNullOrEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = loginState.errorMessage,
                    color = androidx.compose.material.MaterialTheme.colors.error
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            NavigationTextButton(
                primaryTextId = R.string.no_account,
                secondaryTextId = R.string.create_account,
                onClick = { onNavigateToRegister() }
            )
        }
    }
}
