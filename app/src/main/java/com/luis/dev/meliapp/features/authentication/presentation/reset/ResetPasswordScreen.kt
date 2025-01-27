package com.luis.dev.meliapp.features.authentication.presentation.reset

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
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
import com.luis.dev.meliapp.features.authentication.presentation.components.NavigationTextButton

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
                titleResId = R.string.recover_password_title,
                instructionsResId = R.string.recover_password_instructions
            )
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .weight(1f))
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.End
            ){
                EmailTextField(
                    email = state.email,
                    onEmailChange = { onIntent(ResetPasswordIntent.EmailChanged(it)) },
                    isError = state.errorMessage?.contains("Email") == true
                )

            }

            Spacer(modifier = Modifier
                .fillMaxWidth()
                .weight(1f))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                ActionButton(
                    text = "Enviar correo",
                    enabled = !state.isLoading,
                    onClick = { onIntent(ResetPasswordIntent.ResetClicked) }
                )

                Spacer(modifier = Modifier.padding(20.dp))

                NavigationTextButton(
                    primaryTextId = R.string.remembered_password,
                    secondaryTextId = R.string.login_button,
                    onClick = { onNavigateBack() }

                )
            }

            Spacer(modifier = Modifier
                .fillMaxWidth()
                .weight(3f))
        }
    }
}
