package com.luis.dev.meliapp.features.results.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.luis.dev.meliapp.R
import com.luis.dev.meliapp.core.utils.convertToHttps


/**
 * Muestra la imagen del producto.
 *
 * @param thumbnailUrl La URL de la imagen del producto.
 * @param title La descripción del contenido de la imagen para accesibilidad.
 */
@Composable
fun ResultItemProductThumbnail(thumbnailUrl: String, title: String) {
    val secureThumbnailUrl = thumbnailUrl.convertToHttps()
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(secureThumbnailUrl)
            .crossfade(true)
            .build(),
        placeholder = painterResource(R.drawable.no_image_available),
        contentDescription = title,
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .size(150.dp)
            .clip(RoundedCornerShape(8.dp))
    )
}