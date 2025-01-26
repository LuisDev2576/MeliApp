package com.luis.dev.meliapp.features.details.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.luis.dev.meliapp.features.details.data.model.Attribute
import com.luis.dev.meliapp.features.details.data.model.ItemDetailResponse
import com.luis.dev.meliapp.features.details.data.model.Picture
import com.luis.dev.meliapp.features.details.presentation.components.ItemDetailContent
import com.luis.dev.meliapp.features.results.presentation.components.ErrorView
import com.luis.dev.meliapp.features.results.presentation.components.LoadingIndicator
import com.luis.dev.meliapp.features.results.presentation.components.NoResultsView
import com.luis.dev.meliapp.ui.theme.MeliAppTheme

/**
 * Pantalla que muestra los detalles de un producto seleccionado.
 */
@Composable
fun DetailsScreen(
    detailsState: DetailsState,
) {
    when {
        detailsState.isLoading -> {
            LoadingIndicator()
        }
        detailsState.error != null -> {
            ErrorView(errorMessage = detailsState.error)
        }
        detailsState.itemDetail != null -> {
            ItemDetailContent(itemDetail = detailsState.itemDetail)
        }
        else -> {
            NoResultsView()
        }
    }
}

@Preview(showSystemUi = true, device = "id:pixel_7")
@Composable
fun DetailScreenLoadingPreview() {
    val dummyState = DetailsState(isLoading = true)
    MeliAppTheme {
        DetailScreenPreview(dummyState)
    }
}

@Preview(showSystemUi = true, device = "id:pixel_7")
@Composable
fun DetailScreenErrorPreview() {
    val dummyState = DetailsState(error = "No se pudo cargar el detalle del producto.")
    MeliAppTheme {
        DetailScreenPreview(dummyState)
    }
}

@Preview(showSystemUi = true, device = "id:pixel_7")
@Composable
fun DetailScreenSuccessPreview() {
    val dummyItem = ItemDetailResponse(
        id = "MLA1461972881",
        siteId = "MLA",
        title = "Apple iPhone 15 (128 Gb) - Negro",
        price = 1899995.0,
        currencyId = "ARS",
        thumbnail = "http://http2.mlstatic.com/D_779617-MLA71782867320_092023-I.jpg",
        pictures = listOf(
            Picture(id = "779617-MLA71782867320_092023", url = "https://http2.mlstatic.com/D_779617-MLA71782867320_092023-O.jpg")
        ),
        attributes = listOf(
            Attribute(id = "BRAND", name = "Marca", valueName = "Apple"),
            Attribute(id = "COLOR", name = "Color", valueName = "Negro"),
            Attribute(id = "GTIN", name = "Código universal de producto", valueName = "195949035937"),
            Attribute(id = "ITEM_CONDITION", name = "Condición del ítem", valueName = "Nuevo"),
            Attribute(id = "LINE", name = "Línea", valueName = "iPhone 15")
        ),
        warranty = "Garantía de fábrica: 12 meses"
    )

    val dummyState = DetailsState(
        isLoading = false,
        error = null,
        itemDetail = dummyItem
    )

    MeliAppTheme {
        DetailScreenPreview(dummyState)
    }
}

@Composable
fun DetailScreenPreview(uiState: DetailsState) {
    when {
        uiState.isLoading -> {
            LoadingIndicator()
        }
        uiState.error != null -> {
            ErrorView(errorMessage = uiState.error)
        }
        uiState.itemDetail != null -> {
            ItemDetailContent(itemDetail = uiState.itemDetail)
        }
        else -> {
            NoResultsView()
        }
    }
}
