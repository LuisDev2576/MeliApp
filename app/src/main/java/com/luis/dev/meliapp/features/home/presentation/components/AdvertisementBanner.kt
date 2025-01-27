package com.luis.dev.meliapp.features.home.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.luis.dev.meliapp.R

/**
 * Banner publicitario que muestra una imagen con estilo y diseño personalizado.
 *
 * @param imageUrl URL de la imagen que se mostrará en el banner.
 */
@Composable
fun AdvertisementBanner(
    imageUrl: String,
) {
    AsyncImage(
        model = imageUrl,
        placeholder = painterResource(R.drawable.no_image_available),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .padding(16.dp)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(10))
            .fillMaxWidth()
            .height(90.dp)
            .clip(RoundedCornerShape(10))
            .clickable {}
    )
}
