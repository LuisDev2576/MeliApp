package com.luis.dev.meliapp.features.details.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

/**
 * Sección que muestra la garantía del producto.
 */
@Composable
fun WarrantySection(warranty: String) {
    Text(
        text = warranty,
        style = MaterialTheme.typography.bodyLarge,
        color = Color.Gray,
        fontSize = 14.sp
    )
}
