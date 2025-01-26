package com.luis.dev.meliapp.features.authentication.presentation.reset

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.luis.dev.meliapp.R
import com.luis.dev.meliapp.features.authentication.presentation.components.ActionButton
import com.luis.dev.meliapp.features.authentication.presentation.components.EmailTextField
import com.luis.dev.meliapp.features.authentication.presentation.components.NavigationTextButton

@Composable
fun ResetPasswordScreen(
    state: ResetPasswordState,
    onIntent: (ResetPasswordIntent) -> Unit,
    onNavigateBack: () -> Unit
) {
    BackHandler {
        onNavigateBack()
    }

    val context = LocalContext.current
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

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Recuperar Contraseña")

            EmailTextField(
                email = state.email,
                onEmailChange = { onIntent(ResetPasswordIntent.EmailChanged(it)) },
                isError = state.errorMessage?.contains("Email") == true
            )

            ActionButton(
                text = "Enviar correo",
                enabled = !state.isLoading,
                onClick = { onIntent(ResetPasswordIntent.ResetClicked) }
            )

            if (!state.errorMessage.isNullOrEmpty()) {
                Text(
                    text = state.errorMessage,
                    color = androidx.compose.material.MaterialTheme.colors.error
                )
            }

            NavigationTextButton(
                primaryTextId = R.string.remembered_password,
                secondaryTextId = R.string.login_button,
                onClick = { onNavigateBack() }

            )
        }
    }
}
