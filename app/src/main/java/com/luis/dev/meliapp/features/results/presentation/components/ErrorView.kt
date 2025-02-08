package com.luis.dev.meliapp.features.results.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * Muestra un mensaje de error centrado en la pantalla.
 *
 * @param errorMessage El mensaje de error a mostrar.
 */
@Composable
fun ErrorView(errorMessage: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Error: $errorMessage",
            color = Color.White
        )
    }
}

@Preview(showSystemUi = true, device = "id:pixel_7")
@Composable
fun ErrorViewPreview() {
    ErrorView(errorMessage = "Something went wrong")
}