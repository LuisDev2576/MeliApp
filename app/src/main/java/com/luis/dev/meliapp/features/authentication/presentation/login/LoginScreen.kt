package com.luis.dev.meliapp.features.authentication.presentation.login

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.luis.dev.meliapp.R
import com.luis.dev.meliapp.features.authentication.presentation.components.CustomActionButton
import com.luis.dev.meliapp.features.authentication.presentation.components.EmailInputField
import com.luis.dev.meliapp.features.authentication.presentation.components.GreetingMessage
import com.luis.dev.meliapp.features.authentication.presentation.components.ResetPasswordNavigationButton
import com.luis.dev.meliapp.features.authentication.presentation.components.NavigationLinkText
import com.luis.dev.meliapp.features.authentication.presentation.components.PasswordTextField

/**
 * Pantalla de inicio de sesión que permite a los usuarios ingresar su correo electrónico y contraseña,
 * además de opciones para recuperar la contraseña o registrarse.
 *
 * @param loginState Estado actual del proceso de inicio de sesión, incluyendo datos del usuario y mensajes de error.
 * @param onIntent Callback que maneja las intenciones del usuario, como cambiar datos o iniciar sesión.
 * @param onNavigateToRegister Callback que se ejecuta cuando el usuario desea navegar a la pantalla de registro.
 * @param onNavigateToReset Callback que se ejecuta cuando el usuario desea navegar a la pantalla de recuperación de contraseña.
 * @param onNavigateBack Callback que se ejecuta cuando el usuario desea regresar a la pantalla anterior.
 * @param onLoginSuccess Callback que se ejecuta cuando el inicio de sesión se realiza con éxito.
 */
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
        onNavigateBack()
    }

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
            GreetingMessage(
                titleResId = R.string.greeting_message,
                instructionsResId = R.string.login_prompt
            )


            Spacer(modifier = Modifier
                .fillMaxWidth()
                .weight(1f))

            Column(
                horizontalAlignment = Alignment.End
            ) {
                EmailInputField(
                    email = loginState.email,
                    onEmailChange = { onIntent(LoginIntent.EmailChanged(it)) },
                    hasError = loginState.errorMessage?.contains("Email") == true
                )

                Spacer(modifier = Modifier.height(10.dp))

                PasswordTextField(
                    password = loginState.password,
                    onPasswordChange = { onIntent(LoginIntent.PasswordChanged(it)) },
                    isError = loginState.errorMessage?.contains("contraseña") == true,
                    onDone = { onIntent(LoginIntent.LoginClicked) },
                    clearFocusOnDone = true
                )

                ResetPasswordNavigationButton(
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
                CustomActionButton(
                    label = "Iniciar Sesión",
                    isEnabled = !loginState.isLoading,
                    onAction = { onIntent(LoginIntent.LoginClicked) }
                )

                Spacer(modifier = Modifier.height(20.dp))

                NavigationLinkText(
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
