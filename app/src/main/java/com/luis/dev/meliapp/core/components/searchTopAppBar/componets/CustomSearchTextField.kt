package com.luis.dev.meliapp.core.components.searchTopAppBar.componets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

/**
 * Campo de texto personalizado para realizar búsquedas, con diseño redondeado e icono de búsqueda.
 *
 * @param modifier Modificador opcional para personalizar el estilo y comportamiento del campo de texto.
 * @param placeholderText Texto que se muestra como marcador de posición cuando el campo está vacío.
 * @param fontSize Tamaño de fuente para el texto ingresado y el marcador de posición.
 * @param value Texto actual ingresado en el campo.
 * @param onValueChange Callback que se ejecuta cuando el usuario modifica el texto ingresado.
 * @param onDoneActionClick Callback que se ejecuta cuando el usuario presiona el botón "Done" en el teclado.
 */
@Composable
fun CustomSearchTextField(
    modifier: Modifier = Modifier,
    placeholderText: String = "Buscar en Mercado Libre",
    fontSize: TextUnit = MaterialTheme.typography.bodyMedium.fontSize,
    value: String,
    onValueChange: (String) -> Unit,
    onDoneActionClick: (KeyboardActionScope.() -> Unit)
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    BasicTextField(
        modifier = modifier
            .clip(shape = CircleShape)
            .background(
                MaterialTheme.colorScheme.surface,
                MaterialTheme.shapes.small,
            )
            .fillMaxWidth()
            .focusRequester(focusRequester),
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        keyboardActions = KeyboardActions(
            onDone = {
                onDoneActionClick()
                focusManager.clearFocus()
            }
        ),
        singleLine = true,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        textStyle = LocalTextStyle.current.copy(
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = fontSize
        ),
        decorationBox = { innerTextField ->
            Row(
                modifier
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = Color.LightGray
                )

                Box(Modifier.weight(1f)) {
                    if (value.isEmpty()) {
                        Text(
                            text = placeholderText,
                            style = LocalTextStyle.current.copy(
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                                fontSize = fontSize
                            )
                        )
                    }
                    innerTextField()
                }
            }
        }
    )
}


