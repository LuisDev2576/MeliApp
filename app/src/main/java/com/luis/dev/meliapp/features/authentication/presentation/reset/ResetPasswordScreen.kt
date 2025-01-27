package com.luis.dev.meliapp.features.authentication.presentation.reset

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.luis.dev.meliapp.features.authentication.presentation.components.NavigationLinkText

/**
 * Pantalla para restablecer la contraseña de un usuario mediante el envío de un correo electrónico.
 *
 * @param state Estado actual del flujo de restablecimiento de contraseña, incluyendo el correo ingresado y mensajes de error.
 * @param onIntent Callback para manejar las intenciones del usuario, como cambios en el correo electrónico o envío del formulario.
 * @param onNavigateBack Callback que se ejecuta cuando el usuario desea regresar a la pantalla anterior.
 */
@Composable
fun ResetPasswordScreen(
    state: ResetPasswordState,
    onIntent: (ResetPasswordIntent) -> Unit,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    BackHandler {
        onNavigateBack()
    }

    if (state.resetEmailSent) {
        LaunchedEffect(Unit) {
            Toast.makeText(
                context,
                "Correo de recuperación enviado exitosamente",
                Toast.LENGTH_SHORT
            ).show()
            onNavigateBack()
        }
    }

    if (state.errorMessage != null) {
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
            GreetingMessage(
                titleResId = R.string.recover_password_title,
                instructionsResId = R.string.recover_password_instructions
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
            ) {
                EmailInputField(
                    email = state.email,
                    onEmailChange = { onIntent(ResetPasswordIntent.EmailChanged(it)) },
                    hasError = state.errorMessage?.contains("Email") == true
                )
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CustomActionButton(
                    label = "Enviar correo",
                    isEnabled = !state.isLoading,
                    onAction = { onIntent(ResetPasswordIntent.ResetClicked) }
                )

                Spacer(modifier = Modifier.padding(20.dp))

                NavigationLinkText(
                    primaryTextId = R.string.remembered_password,
                    secondaryTextId = R.string.login_button,
                    onClick = { onNavigateBack() }
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
