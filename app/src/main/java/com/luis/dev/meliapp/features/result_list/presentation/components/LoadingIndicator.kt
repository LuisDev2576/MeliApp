package com.luis.dev.meliapp.features.result_list.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.luis.dev.meliapp.ui.theme.MeliAppTheme

/**
 * Muestra un indicador de carga centrado en la pantalla.
 */
@Composable
fun LoadingIndicator() {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Preview(showSystemUi = true, device = "id:pixel_7")
@Composable
fun LoadingIndicatorPreview() {
    MeliAppTheme {
        LoadingIndicator()
    }
}