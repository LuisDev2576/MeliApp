package com.luis.dev.meliapp.core.components.searchTopAppBar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.tooling.preview.Preview
import com.luis.dev.meliapp.core.components.searchTopAppBar.components.LocationButton
import com.luis.dev.meliapp.core.components.searchTopAppBar.componets.CustomSearchTextField
import com.luis.dev.meliapp.ui.theme.MeliAppTheme

/**
 * Barra de búsqueda superior con opciones de navegación y búsqueda.
 *
 * @param state Representa el estado actual del componente de búsqueda, incluyendo la consulta actual.
 * @param onIntent Callback que se ejecuta cuando se realiza una acción en el componente de búsqueda,
 * como actualizar el texto o realizar una búsqueda.
 * @param modifier Modificador opcional para personalizar el estilo del componente.
 * @param onNavigateToResultScreen Callback para navegar a la pantalla de resultados con la consulta ingresada.
 */
@Composable
fun SearchTopBar(
    state: SearchBarState,
    onIntent: (SearchBarIntent) -> Unit,
    modifier: Modifier = Modifier,
    onNavigateToResultScreen: (String) -> Unit
) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { /* Open side menu */ }) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = null)
            }

            CustomSearchTextField(
                modifier = Modifier.weight(1f),
                value = state.query,
                onValueChange = { newText ->
                    onIntent(SearchBarIntent.UpdateQuery(newText))
                },
                onDoneActionClick = {
                    onNavigateToResultScreen(state.query)
                    onIntent(SearchBarIntent.UpdateQuery(""))
                }
            )

            IconButton(onClick = { /* Navigate to shopping cart */ }) {
                Icon(imageVector = Icons.Outlined.ShoppingCart, contentDescription = null)
            }
        }
        LocationButton { /* Open location dialog */ }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchTopBarPreview() {
    MeliAppTheme {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.surface)) {
            SearchTopBar(
                state = SearchBarState(query = ""),
                onIntent = {},
                onNavigateToResultScreen = {}
            )
        }
    }
}