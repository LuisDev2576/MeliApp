package com.luis.dev.meliapp.features.results.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


/**
 * Dibuja una línea horizontal divisoria.
 *
 * @param color El color de la línea divisoria.
 */
@Composable
fun ResultItemHorizontalDivider(color: Color = Color.LightGray.copy(alpha = 0.5f)) {
    HorizontalDivider(
        color = color,
        thickness = 1.dp,
        modifier = Modifier.fillMaxWidth()
    )
}