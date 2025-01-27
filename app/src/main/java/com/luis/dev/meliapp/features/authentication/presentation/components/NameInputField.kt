package com.luis.dev.meliapp.features.authentication.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.luis.dev.meliapp.R

/**
 * Campo de texto personalizado para ingresar un nombre.
 *
 * @param name Texto que representa el nombre ingresado.
 * @param onNameChange Callback que se ejecuta cuando el usuario modifica el texto del nombre.
 * @param hasError Indica si hay un error de validación en el campo de texto.
 */
@Composable
fun NameInputField(
    name: String,
    onNameChange: (String) -> Unit,
    hasError: Boolean
) {
    val focusManager = LocalFocusManager.current

    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = name,
            singleLine = true,
            onValueChange = { onNameChange(it) },
            textStyle = MaterialTheme.typography.bodyLarge,
            placeholder = {
                Text(
                    text = stringResource(id = R.string.name_placeholder),
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            keyboardActions = KeyboardActions(
                onDone = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            isError = hasError,
            shape = RoundedCornerShape(20),
            modifier = Modifier
                .widthIn(max = 600.dp)
                .fillMaxWidth()
                .height(60.dp)
                .align(Alignment.CenterHorizontally),
            colors = TextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                disabledTextColor = MaterialTheme.colorScheme.onSurface,
                errorTextColor = MaterialTheme.colorScheme.error,
                focusedContainerColor = MaterialTheme.colorScheme.background,
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                disabledContainerColor = MaterialTheme.colorScheme.background,
                errorContainerColor = MaterialTheme.colorScheme.background,
                cursorColor = MaterialTheme.colorScheme.primary,
                errorCursorColor = MaterialTheme.colorScheme.primary,
                focusedIndicatorColor = Color.LightGray,
                unfocusedIndicatorColor = Color.LightGray,
                disabledIndicatorColor = Color.LightGray,
                errorIndicatorColor = Color.LightGray,
                focusedPlaceholderColor = MaterialTheme.colorScheme.onSurface,
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface,
                disabledPlaceholderColor = MaterialTheme.colorScheme.onSurface,
                errorPlaceholderColor = MaterialTheme.colorScheme.error,
            )
        )
    }
}
