package com.luis.dev.meliapp.features.result_list.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

/**
 * Muestra un mensaje indicando que no se encontraron resultados.
 */
@Composable
fun NoResultsView() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "No se encontraron resultados.")
    }
}

@Preview(showSystemUi = true, device = "id:pixel_7")
@Composable
fun NoResultsViewPreview() {
    NoResultsView()
}