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
import com.luis.dev.meliapp.features.authentication.presentation.components.ConfirmPasswordTextField
import com.luis.dev.meliapp.features.authentication.presentation.components.EmailTextField
import com.luis.dev.meliapp.features.authentication.presentation.components.Greeting
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
    if(state.errorMessage != null){
        LaunchedEffect(state.errorMessage) {
            focusManager.clearFocus()
            Toast.makeText(context, state.errorMessage, Toast.LENGTH_LONG).show()
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
            Greeting(
                titleResId = R.string.create_account_title,
            )

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.End
            ){
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
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                ActionButton(
                    text = "Registrarse",
                    enabled = !state.isLoading,
                    onClick = { onIntent(RegisterIntent.RegisterClicked) }
                )

                Spacer(modifier = Modifier.height(20.dp))

                NavigationTextButton(
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
