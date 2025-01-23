package com.luis.dev.meliapp.features.search.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.unit.sp

@Composable
fun MyTopAppBar(
    state: SearchState,
    onIntent: (SearchIntent) -> Unit
){
    Column(
        modifier = Modifier.background(MaterialTheme.colorScheme.primary)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { /* Abrir menú lateral */ }) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = null)
            }

            CustomSearchTextField(
                modifier = Modifier.weight(1f),
                value = state.query,
                onValueChange = { newText ->
                    onIntent(SearchIntent.UpdateQuery(newText))
                },
                onDoneActionClick = {
                    onIntent(SearchIntent.PerformSearch)
                }
            )

            IconButton(onClick = { /* Navegar al carrito de compras */ }) {
                Icon(imageVector = Icons.Outlined.ShoppingCart, contentDescription = null)
            }
        }
        IngresaTuUbicacionButton { /* Abrir dialog de ubicación */ }
    }
}


@Composable
private fun CustomSearchTextField(
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
            .focusRequester(focusRequester), // Asignamos el focusRequester
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        keyboardActions = KeyboardActions(
            onDone = {
                // Llamamos a la acción personalizada
                onDoneActionClick()
                // Limpiamos el foco
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


@Composable
private fun IngresaTuUbicacionButton(
    onClick: () -> Unit
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(start = 4.dp, top = 8.dp, bottom = 8.dp)
            .clip(shape = CircleShape)
            .clickable {
                onClick()
            }
            .padding(horizontal = 8.dp)
    ) {
        Icon(
            imageVector = Icons.Outlined.LocationOn,
            contentDescription = null,
            tint = Color.DarkGray,
            modifier = Modifier
                .size(16.dp)
        )
        Text(
            "Ingresa tu ubicación",
            fontSize = 14.sp,
            color = Color.DarkGray
        )
        Icon(
            imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
            contentDescription = null,
            tint = Color.DarkGray,
            modifier = Modifier
                .size(16.dp)
        )
    }
}