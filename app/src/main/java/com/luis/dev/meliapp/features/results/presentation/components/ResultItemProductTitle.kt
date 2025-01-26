package com.luis.dev.meliapp.features.results.presentation.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp


/**
 * Muestra el título del producto.
 *
 * @param title El título del producto.
 */
@Composable
fun ResultItemProductTitle(title: String) {
    Text(
        text = title,
        color = Color.Black,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        fontSize = 14.sp
    )
}
