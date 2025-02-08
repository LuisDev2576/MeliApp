package com.luis.dev.meliapp.features.authentication.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

/**
 * Componente que muestra un texto de navegación compuesto por un texto primario estático y un texto secundario interactivo.
 *
 * @param primaryTextId ID del recurso de texto que se mostrará como texto primario.
 * @param secondaryTextId ID del recurso de texto que se mostrará como texto secundario interactivo.
 * @param onClick Acción que se ejecuta cuando el usuario hace clic en el texto secundario.
 * @param modifier Modificador opcional para personalizar el estilo y el diseño del componente.
 */
@Composable
fun NavigationLinkText(
    primaryTextId: Int,
    secondaryTextId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = stringResource(id = primaryTextId),
            modifier = Modifier
                .clip(RoundedCornerShape(20))
                .padding(horizontal = 10.dp),
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = stringResource(id = secondaryTextId),
            modifier = Modifier
                .clip(RoundedCornerShape(20))
                .clickable(onClick = onClick)
                .padding(horizontal = 10.dp, vertical = 5.dp),
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Blue,
            fontWeight = FontWeight.Medium
        )
    }
}