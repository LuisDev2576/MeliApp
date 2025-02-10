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
import com.luis.dev.meliapp.features.authentication.domain.models.LoginError
import com.luis.dev.meliapp.features.authentication.presentation.components.CustomActionButton
import com.luis.dev.meliapp.features.authentication.presentation.components.EmailInputField
import com.luis.dev.meliapp.features.authentication.presentation.components.GreetingMessage
import com.luis.dev.meliapp.features.authentication.presentation.components.NavigationLinkText
import com.luis.dev.meliapp.features.authentication.presentation.components.PasswordTextField
import com.luis.dev.meliapp.features.authentication.presentation.components.ResetPasswordNavigationButton

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

    // Mapeamos el error tipificado a un string usando los recursos.
    loginState.error?.let { error ->
        val errorMessage = when (error) {
            is LoginError.InvalidEmail -> context.getString(R.string.error_invalid_email)
            is LoginError.ShortPassword -> context.getString(R.string.error_password_short)
            is LoginError.FirebaseError -> error.message ?: context.getString(R.string.error_try_again)
        }
        LaunchedEffect(error) {
            focusManager.clearFocus()
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
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

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )

            Column(
                horizontalAlignment = Alignment.End
            ) {
                EmailInputField(
                    email = loginState.email,
                    onEmailChange = { onIntent(LoginIntent.EmailChanged(it)) },
                    hasError = loginState.error is LoginError.InvalidEmail
                )

                Spacer(modifier = Modifier.height(10.dp))

                PasswordTextField(
                    password = loginState.password,
                    onPasswordChange = { onIntent(LoginIntent.PasswordChanged(it)) },
                    isError = loginState.error is LoginError.ShortPassword || loginState.error is LoginError.FirebaseError,
                    onDone = { onIntent(LoginIntent.LoginClicked) },
                    clearFocusOnDone = true
                )

                ResetPasswordNavigationButton(
                    onClick = { onNavigateToReset() }
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
                    labelResId = R.string.login_button,
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

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(3f)
            )
        }
    }
}