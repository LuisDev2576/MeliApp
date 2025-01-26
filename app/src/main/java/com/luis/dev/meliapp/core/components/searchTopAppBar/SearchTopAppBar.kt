package com.luis.dev.meliapp.core.components.searchTopAppBar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.luis.dev.meliapp.core.components.searchTopAppBar.componets.CustomSearchTextField
import com.luis.dev.meliapp.core.components.searchTopAppBar.componets.IngresaTuUbicacionButton

@Composable
fun SearchTopAppBar(
    state: SearchBarState,
    onIntent: (SearchBarIntent) -> Unit,
    modifier: Modifier = Modifier,
    navToResultScreen: (String) -> Unit
){
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { /* Abrir menú lateral */ }) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = null)
            }

            CustomSearchTextField(
                modifier = Modifier.weight(1f),
                value = state.query,
                onValueChange = { newText ->
                    onIntent(SearchBarIntent.UpdateQuery(newText))
                },
                onDoneActionClick = {
                    navToResultScreen(state.query)
                    onIntent(SearchBarIntent.UpdateQuery(""))
                }
            )

            IconButton(onClick = { /* Navegar al carrito de compras */ }) {
                Icon(imageVector = Icons.Outlined.ShoppingCart, contentDescription = null)
            }
        }
        IngresaTuUbicacionButton { /* Abrir dialog de ubicación */ }
    }
}
