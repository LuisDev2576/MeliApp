package com.luis.dev.meliapp.features.authentication.presentation.register

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import com.luis.dev.meliapp.features.authentication.domain.models.RegisterError
import com.luis.dev.meliapp.features.authentication.presentation.components.ConfirmPasswordField
import com.luis.dev.meliapp.features.authentication.presentation.components.CustomActionButton
import com.luis.dev.meliapp.features.authentication.presentation.components.EmailInputField
import com.luis.dev.meliapp.features.authentication.presentation.components.GreetingMessage
import com.luis.dev.meliapp.features.authentication.presentation.components.NameInputField
import com.luis.dev.meliapp.features.authentication.presentation.components.NavigationLinkText
import com.luis.dev.meliapp.features.authentication.presentation.components.PasswordTextField

/**
 * Pantalla de registro que permite a los usuarios crear una cuenta mediante el ingreso de sus datos personales,
 * correo electrónico y contraseña.
 *
 * @param state Estado actual del proceso de registro, que incluye datos ingresados por el usuario y errores tipados.
 * @param onIntent Callback para manejar las intenciones del usuario, como cambios en los campos de texto o registrar una cuenta.
 * @param onNavigateToLogin Callback que se ejecuta cuando el usuario desea regresar a la pantalla de inicio de sesión.
 * @param onRegistrationSuccess Callback que se ejecuta cuando el registro se completa con éxito.
 */
@Composable
fun RegisterScreen(
    state: RegisterState,
    onIntent: (RegisterIntent) -> Unit,
    onNavigateToLogin: () -> Unit,
    onRegistrationSuccess: () -> Unit
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    BackHandler {
        onNavigateToLogin()
    }

    if (state.registeredSuccess) {
        LaunchedEffect(Unit) {
            onRegistrationSuccess()
            focusManager.clearFocus()
        }
    }

    state.error?.let { error ->
        val errorMessage = when (error) {
            is RegisterError.EmptyName -> context.getString(R.string.error_name_empty)
            is RegisterError.InvalidEmail -> context.getString(R.string.error_email_invalid)
            is RegisterError.WeakPassword -> context.getString(R.string.error_short_password)
            is RegisterError.PasswordMismatch -> context.getString(R.string.error_passwords_not_matched)
            is RegisterError.FirebaseError -> error.message ?: context.getString(R.string.error_try_again)
        }
        LaunchedEffect(error) {
            focusManager.clearFocus()
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        }
    }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GreetingMessage(
                titleResId = R.string.create_account_title
            )

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.End
            ) {
                // Se comprueba que el error corresponda al campo (tipo EmptyName)
                NameInputField(
                    name = state.fullName,
                    onNameChange = { onIntent(RegisterIntent.FullNameChanged(it)) },
                    hasError = state.error is RegisterError.EmptyName
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Se comprueba que el error corresponda al campo (tipo InvalidEmail)
                EmailInputField(
                    email = state.email,
                    onEmailChange = { onIntent(RegisterIntent.EmailChanged(it)) },
                    hasError = state.error is RegisterError.InvalidEmail
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Se comprueba que el error corresponda al campo (tipo WeakPassword)
                PasswordTextField(
                    password = state.password,
                    onPasswordChange = { onIntent(RegisterIntent.PasswordChanged(it)) },
                    isError = state.error is RegisterError.WeakPassword,
                    onDone = {},
                    clearFocusOnDone = false
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Se comprueba que el error corresponda al campo (tipo PasswordMismatch)
                ConfirmPasswordField(
                    confirmPassword = state.confirmPassword,
                    onConfirmPasswordChange = { onIntent(RegisterIntent.ConfirmPasswordChanged(it)) },
                    hasError = state.error is RegisterError.PasswordMismatch,
                    onDone = { onIntent(RegisterIntent.RegisterClicked) }
                )
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CustomActionButton(
                    labelResId = R.string.create_account,
                    isEnabled = !state.isLoading,
                    onAction = { onIntent(RegisterIntent.RegisterClicked) }
                )

                Spacer(modifier = Modifier.height(20.dp))

                NavigationLinkText(
                    primaryTextId = R.string.already_have_account,
                    secondaryTextId = R.string.login_button,
                    onClick = { onNavigateToLogin() }
                )
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(3f)
            )
        }
    }
}