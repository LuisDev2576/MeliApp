package com.luis.dev.meliapp.features.authentication.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.luis.dev.meliapp.ui.theme.MeliAppTheme

@Composable
fun ActionButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        enabled = enabled,
        onClick = { onClick() },
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Blue,
            disabledContainerColor = Color.Blue.copy(alpha = 0.3f),
        ),
        shape = RoundedCornerShape(10)
    ) {
        if (enabled) {
            Text(text = text, color = Color.White)
        } else {
            CircularProgressIndicator(color = Color.White)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ActionButtonPreviewEnabled() {
    MeliAppTheme {
        Box(modifier = Modifier.background(Color.White)){
            ActionButton(text = "Click Me", onClick = { /* Handle click */ })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ActionButtonPreviewDisabled() {
    MeliAppTheme {
        Box(modifier = Modifier.background(Color.White)){
            ActionButton(text = "Loading", enabled = false, onClick = { /* Handle click */ })
        }
    }
}