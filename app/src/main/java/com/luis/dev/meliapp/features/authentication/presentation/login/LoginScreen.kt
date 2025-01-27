package com.luis.dev.meliapp.features.authentication.presentation.login

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.luis.dev.meliapp.R
import com.luis.dev.meliapp.features.authentication.presentation.components.ActionButton
import com.luis.dev.meliapp.features.authentication.presentation.components.EmailTextField
import com.luis.dev.meliapp.features.authentication.presentation.components.Greeting
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
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    BackHandler {
        // Controla el back si lo deseas
        onNavigateBack()
    }

    // Si el login tuvo éxito, podrías navegar
    if (loginState.success) {
        LaunchedEffect(Unit) {
            onLoginSuccess()
            focusManager.clearFocus()
        }
    }

    if(loginState.errorMessage != null){
        LaunchedEffect(loginState.errorMessage) {
            focusManager.clearFocus()
            Toast.makeText(context, loginState.errorMessage, Toast.LENGTH_LONG).show()
        }
    }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Greeting(
                titleResId = R.string.greeting_message,
                instructionsResId = R.string.login_prompt
            )


            Spacer(modifier = Modifier
                .fillMaxWidth()
                .weight(1f))

            Column(
                horizontalAlignment = Alignment.End
            ) {
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
            }

            Spacer(modifier = Modifier
                .fillMaxWidth()
                .weight(1f))
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                ActionButton(
                    text = "Iniciar Sesión",
                    enabled = !loginState.isLoading,
                    onClick = { onIntent(LoginIntent.LoginClicked) }
                )

                Spacer(modifier = Modifier.height(20.dp))

                NavigationTextButton(
                    primaryTextId = R.string.no_account,
                    secondaryTextId = R.string.create_account,
                    onClick = { onNavigateToRegister() }
                )
            }
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .weight(3f))


        }
    }
}
