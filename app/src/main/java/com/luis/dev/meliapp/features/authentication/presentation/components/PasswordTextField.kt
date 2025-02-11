package com.luis.dev.meliapp.features.authentication.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.luis.dev.meliapp.R

/**
 * Campo de texto personalizado para ingresar contraseñas.
 * Incluye funcionalidad para mostrar/ocultar la contraseña y opciones para manejar el enfoque al completar.
 *
 * @param password Texto que representa la contraseña ingresada.
 * @param onPasswordChange Callback que se ejecuta cuando el usuario modifica el texto de la contraseña.
 * @param isError Indica si hay un error de validación en el campo de texto.
 * @param onDone Callback que se ejecuta cuando el usuario presiona el botón "Done" en el teclado.
 * @param clearFocusOnDone Indica si el foco debe eliminarse al completar (true) o moverse al siguiente campo (false).
 */
@Composable
fun PasswordTextField(
    password: String,
    onPasswordChange: (String) -> Unit,
    isError: Boolean,
    onDone: () -> Unit,
    clearFocusOnDone: Boolean = false
) {
    val focusManager = LocalFocusManager.current
    var visibility by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = password,
            onValueChange = { onPasswordChange(it) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                if (visibility) {
                    Icon(
                        imageVector = Icons.Default.VisibilityOff,
                        contentDescription = null,
                        modifier = Modifier.clickable { visibility = false }
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Visibility,
                        contentDescription = null,
                        modifier = Modifier.clickable { visibility = true }
                    )
                }
            },
            textStyle = MaterialTheme.typography.bodyLarge,
            visualTransformation = if (visibility) VisualTransformation.None else PasswordVisualTransformation(),
            singleLine = true,
            placeholder = {
                Text(
                    text = stringResource(id = R.string.password_placeholder),
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            keyboardActions = KeyboardActions(onDone = {
                if (clearFocusOnDone) {
                    focusManager.clearFocus()
                } else {
                    focusManager.moveFocus(FocusDirection.Next)
                }
                onDone()
            }),
            isError = isError,
            shape = RoundedCornerShape(20),
            modifier = Modifier
                .widthIn(max = 600.dp)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            colors = TextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
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
                focusedTrailingIconColor = MaterialTheme.colorScheme.onSurface,
                unfocusedTrailingIconColor = MaterialTheme.colorScheme.onSurface,
                disabledTrailingIconColor = MaterialTheme.colorScheme.onSurface,
                errorTrailingIconColor = MaterialTheme.colorScheme.onSurface
            )
        )
    }
}