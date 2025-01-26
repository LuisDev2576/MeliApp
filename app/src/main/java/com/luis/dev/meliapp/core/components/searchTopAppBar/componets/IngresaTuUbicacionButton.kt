package com.luis.dev.meliapp.core.components.searchTopAppBar.componets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun IngresaTuUbicacionButton(
    onClick: () -> Unit
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(start = 4.dp, top = 8.dp, bottom = 8.dp)
            .clip(shape = CircleShape)
            .clickable {
                onClick()
            }
            .padding(horizontal = 8.dp)
    ) {
        Icon(
            imageVector = Icons.Outlined.LocationOn,
            contentDescription = null,
            tint = Color.DarkGray,
            modifier = Modifier
                .size(16.dp)
        )
        Text(
            "Ingresa tu ubicación",
            fontSize = 14.sp,
            color = Color.DarkGray
        )
        Icon(
            imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
            contentDescription = null,
            tint = Color.DarkGray,
            modifier = Modifier
                .size(16.dp)
        )
    }
}