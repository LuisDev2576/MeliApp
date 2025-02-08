package com.luis.dev.meliapp.features.details.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.luis.dev.meliapp.R

/**
 * Carrusel de imágenes que permite la navegación horizontal entre múltiples imágenes y muestra indicadores de paginación.
 *
 * @param imageList Lista de URLs de las imágenes que se mostrarán en el carrusel.
 * @param modifier Modificador opcional para personalizar el diseño del carrusel.
 */
@OptIn(ExperimentalPagerApi::class)
@Composable
fun ImageCarousel(
    imageList: List<String>,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState()

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            count = imageList.size,
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 0.dp),
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            CarouselItem(imageUrl = imageList[page])
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
        ) {
            imageList.forEachIndexed { index, _ ->
                val color = if (pagerState.currentPage == index) Color.Blue else Color.LightGray
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(color)
                )
            }
        }
    }
}

@Composable
private fun CarouselItem(imageUrl: String) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        placeholder = painterResource(R.drawable.no_image_available),
        contentDescription = imageUrl,
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .height(300.dp)
            .clip(RoundedCornerShape(8.dp))
    )
}