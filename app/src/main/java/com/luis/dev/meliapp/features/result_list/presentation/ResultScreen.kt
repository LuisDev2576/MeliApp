package com.luis.dev.meliapp.features.result_list.presentation

import Attribute
import Installments
import SearchResultItem
import Shipping
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.luis.dev.meliapp.features.result_list.presentation.components.ErrorView
import com.luis.dev.meliapp.features.result_list.presentation.components.LoadingIndicator
import com.luis.dev.meliapp.features.result_list.presentation.components.NoResultsView
import com.luis.dev.meliapp.features.result_list.presentation.components.ResultsList
import com.luis.dev.meliapp.features.search.presentation.MyTopAppBar
import com.luis.dev.meliapp.features.search.presentation.SearchIntent
import com.luis.dev.meliapp.features.search.presentation.SearchState
import com.luis.dev.meliapp.ui.theme.MeliAppTheme

/**
 * Componente principal que representa la pantalla de resultados de búsqueda.
 *
 * @param padding Los valores de padding aplicados a la pantalla.
 * @param state El estado actual de la búsqueda, que contiene información como resultados, errores y estado de carga.
 * @param onIntent Lambda que maneja los intentos de usuario, como interacciones en la barra de búsqueda.
 */
@Composable
fun ResultScreen(
    padding: PaddingValues,
    state: SearchState,
    onIntent: (SearchIntent) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(padding)
    ) {
        MyTopAppBar(
            state = state,
            onIntent = onIntent
        )

        when {
            state.isLoading -> {
                LoadingIndicator()
            }
            state.error != null -> {
                ErrorView(errorMessage = state.error)
            }
            state.results.isNotEmpty() -> {
                ResultsList(results = state.results)
            }
            else -> {
                NoResultsView()
            }
        }
    }
}


@Preview(showSystemUi = true, device = "id:pixel_7")
@Composable
fun ResultScreenLoadingPreview() {
    val dummyState = SearchState(isLoading = true,)
    MeliAppTheme {
        ResultScreen(
            padding = PaddingValues(top = 24.dp),
            state = dummyState,
            onIntent = {}
        )
    }
}

@Preview(showSystemUi = true, device = "id:pixel_7")
@Composable
fun ResultScreenErrorPreview() {
    val dummyState = SearchState(error = "Something went wrong")
    MeliAppTheme {
        ResultScreen(
            padding = PaddingValues(top = 24.dp),
            state = dummyState,
            onIntent = {}
        )
    }
}

@Preview(showSystemUi = true, device = "id:pixel_7")
@Composable
fun ResultScreenNoResultsPreview() {
    val dummyState = SearchState(results = emptyList())
    MeliAppTheme {
        ResultScreen(
            padding = PaddingValues(top = 24.dp),
            state = dummyState,
            onIntent = {}
        )
    }
}

@Preview(showSystemUi = true, device = "id:pixel_7")
@Composable
fun ResultScreenSuccessPreview() {
    val item1 = SearchResultItem(
        id = "MLA1449414435",
        title = "Apple Macbook Air (13 Pulgadas, 2020, Chip M1, 256 Gb De Ssd, 8 Gb De Ram) - Oro",
        price = 1399999.0,
        thumbnail = "http://http2.mlstatic.com/D_604544-MLU75179296686_032024-I.jpg",
        permalink = "https://www.mercadolibre.com.ar/apple-macbook-air-13-pulgadas-2020-chip-m1-256-gb-de-ssd-8-gb-de-ram-oro/p/MLA17828522#wid=MLA1449414435&sid=unknown",
        officialStoreName = null,
        originalPrice = null,
        shipping = Shipping(
            freeShipping = true
        ),
        installments = Installments(
            quantity = 6,
            amount = 314673.11,
            rate = 34.86,
            currencyId = "ARS"
        ),
        attributes = listOf(
            Attribute(id = "BRAND", name = "Marca", valueName = "Apple"),
            Attribute(id = "COLOR", name = "Color", valueName = "Oro"),
            Attribute(id = "DETAILED_MODEL", name = "Modelo detallado", valueName = "MGN93FN/A"),
            Attribute(id = "GTIN", name = "Código universal de producto", valueName = "194252049013"),
            Attribute(id = "ITEM_CONDITION", name = "Condición del ítem", valueName = "Nuevo"),
            Attribute(id = "LINE", name = "Línea", valueName = "MacBook Air"),
            Attribute(id = "MAIN_COLOR", name = "Color principal", valueName = "Dorado"),
            Attribute(id = "MODEL", name = "Modelo", valueName = "A2337"),
            Attribute(id = "PROCESSOR_BRAND", name = "Marca del procesador", valueName = "Apple"),
            Attribute(id = "PROCESSOR_MODEL", name = "Modelo del procesador", valueName = "M1"),
            Attribute(id = "WEIGHT", name = "Peso", valueName = "1.29 kg")
        )
    )

    val item2 = SearchResultItem(
        id = "MLA1454455585",
        title = "Apple iPhone 11 (64 Gb) - Negro",
        price = 1199999.0,
        thumbnail = "http://http2.mlstatic.com/D_656548-MLA46114829749_052021-I.jpg",
        permalink = "https://www.mercadolibre.com.ar/apple-iphone-11-64-gb-negro/p/MLA15149561#wid=MLA1454455585&sid=unknown",
        officialStoreName = "Mercado Libre",
        originalPrice = 1386668.11,
        shipping = Shipping(
            freeShipping = true
        ),
        installments = Installments(
            quantity = 18,
            amount = 66666.61,
            rate = 0.0,
            currencyId = "ARS"
        ),
        attributes = listOf(
            Attribute(id = "BRAND", name = "Marca", valueName = "Apple"),
            Attribute(id = "COLOR", name = "Color", valueName = "Negro"),
            Attribute(id = "DETAILED_MODEL", name = "Modelo detallado", valueName = "Iphone 11"),
            Attribute(id = "GPU_MODEL", name = "Modelo de GPU", valueName = "Apple GPU MP4"),
            Attribute(id = "GTIN", name = "Código universal de producto", valueName = "194252097236,194252097212"),
            Attribute(id = "ITEM_CONDITION", name = "Condición del ítem", valueName = "Nuevo"),
            Attribute(id = "LINE", name = "Línea", valueName = "iPhone 11"),
            Attribute(id = "MAIN_COLOR", name = "Color principal", valueName = "Negro"),
            Attribute(id = "MODEL", name = "Modelo", valueName = "iPhone 11"),
            Attribute(id = "PACKAGE_LENGTH", name = "Largo del paquete", valueName = "16.8 cm"),
            Attribute(id = "PACKAGE_WEIGHT", name = "Peso del paquete", valueName = "400 g"),
            Attribute(id = "PROCESSOR_MODEL", name = "Modelo del procesador", valueName = "Apple A13 Bionic"),
            Attribute(id = "WEIGHT", name = "Peso", valueName = "194 g")
        )
    )

    val item3 = SearchResultItem(
        id = "MLA1716911282",
        title = "Cargador iPhone 20w + Cable Usb C Carga Rápida Original",
        price = 40999.0,
        thumbnail = "http://http2.mlstatic.com/D_659186-MLA75355193381_032024-O.jpg",
        permalink = "https://articulo.mercadolibre.com.ar/MLA-1716911282-cargador-iphone-20w-cable-usb-c-carga-rapida-original-_JM",
        officialStoreName = null,
        originalPrice = null,
        shipping = Shipping(
            freeShipping = true
        ),
        installments = Installments(
            quantity = 6,
            amount = 9215.21,
            rate = 34.86,
            currencyId = "ARS"
        ),
        attributes = listOf(
            Attribute(id = "BRAND", name = "Marca del cargador", valueName = "Apple"),
            Attribute(id = "ITEM_CONDITION", name = "Condición del ítem", valueName = "Nuevo"),
            Attribute(id = "MODEL", name = "Modelo del cargador", valueName = "20W Fast Charter")
        )
    )

    val item4 = SearchResultItem(
        id = "MLA1465505497",
        title = "Apple Pencil Blanco 1ra Generación. Lápiz Optico Con Adaptador Usb-c Y Adaptador De Lightning.",
        price = 199200.0,
        thumbnail = "http://http2.mlstatic.com/D_755523-MLU74859489643_032024-I.jpg",
        permalink = "https://www.mercadolibre.com.ar/apple-pencil-blanco-1ra-generacion-lapiz-optico-con-adaptador-usb-c-y-adaptador-de-lightning/p/MLA27918950#wid=MLA1465505497&sid=unknown",
        officialStoreName = null,
        originalPrice = 240000.0,
        shipping = Shipping(
            freeShipping = true
        ),
        installments = Installments(
            quantity = 6,
            amount = 44773.52,
            rate = 34.86,
            currencyId = "ARS"
        ),
        attributes = listOf(
            Attribute(id = "BRAND", name = "Marca", valueName = "Apple"),
            Attribute(id = "COLOR", name = "Color", valueName = "Blanco"),
            Attribute(id = "GTIN", name = "Código universal de producto", valueName = "194253687870"),
            Attribute(id = "ITEM_CONDITION", name = "Condición del ítem", valueName = "Nuevo"),
            Attribute(id = "LENGTH", name = "Largo", valueName = "155 mm"),
            Attribute(id = "LINE", name = "Línea", valueName = "Pencil 1ª Generacion"),
            Attribute(id = "MODEL", name = "Modelo", valueName = "Pencil 1ª Generacion"),
            Attribute(id = "PACKAGE_LENGTH", name = "Largo del paquete", valueName = "22 cm"),
            Attribute(id = "PACKAGE_WEIGHT", name = "Peso del paquete", valueName = "140 g"),
            Attribute(id = "WEIGHT", name = "Peso", valueName = "20.7 g")
        )
    )

    val item5 = SearchResultItem(
        id = "MLA1430096665",
        title = "Pencil Carrello Id766 Para iPad Apple Optico Capacitivo Color Blanco",
        price = 39999.2,
        thumbnail = "http://http2.mlstatic.com/D_726940-MLU75926838708_042024-I.jpg",
        permalink = "https://www.mercadolibre.com.ar/pencil-carrello-id766-para-ipad-apple-optico-capacitivo-color-blanco/p/MLA36338291#wid=MLA1430096665&sid=unknown",
        officialStoreName = "Carrello",
        originalPrice = 49999.0,
        shipping = Shipping(
            freeShipping = true
        ),
        installments = Installments(
            quantity = 6,
            amount = 8990.49,
            rate = 34.86,
            currencyId = "ARS"
        ),
        attributes = listOf(
            Attribute(id = "BRAND", name = "Marca", valueName = "Carrello"),
            Attribute(id = "COLOR", name = "Color", valueName = "Blanco"),
            Attribute(id = "GTIN", name = "Código universal de producto", valueName = "0664554227742"),
            Attribute(id = "ITEM_CONDITION", name = "Condición del ítem", valueName = "Nuevo"),
            Attribute(id = "LENGTH", name = "Largo", valueName = "166 mm"),
            Attribute(id = "LINE", name = "Línea", valueName = "Pencil"),
            Attribute(id = "MODEL", name = "Modelo", valueName = "ID766"),
            Attribute(id = "PACKAGE_LENGTH", name = "Largo del paquete", valueName = "2 cm"),
            Attribute(id = "PACKAGE_WEIGHT", name = "Peso del paquete", valueName = "60 g"),
            Attribute(id = "WEIGHT", name = "Peso", valueName = "16 g")
        )
    )

    val item6 = SearchResultItem(
        id = "MLA1461972881",
        title = "Apple iPhone 15 (128 Gb) - Negro",
        price = 1899995.0,
        thumbnail = "http://http2.mlstatic.com/D_779617-MLA71782867320_092023-I.jpg",
        permalink = "https://www.mercadolibre.com.ar/apple-iphone-15-128-gb-negro/p/MLA27172677#wid=MLA1461972881&sid=unknown",
        officialStoreName = null,
        originalPrice = null,
        shipping = Shipping(
            freeShipping = true
        ),
        installments = Installments(
            quantity = 12,
            amount = 158332.92,
            rate = 0.0,
            currencyId = "ARS"
        ),
        attributes = listOf(
            Attribute(id = "BRAND", name = "Marca", valueName = "Apple"),
            Attribute(id = "COLOR", name = "Color", valueName = "Negro"),
            Attribute(id = "GTIN", name = "Código universal de producto", valueName = "195949035937"),
            Attribute(id = "ITEM_CONDITION", name = "Condición del ítem", valueName = "Nuevo"),
            Attribute(id = "LINE", name = "Línea", valueName = "iPhone 15"),
            Attribute(id = "MAIN_COLOR", name = "Color principal", valueName = "Negro"),
            Attribute(id = "MODEL", name = "Modelo", valueName = "iPhone 15"),
            Attribute(id = "PROCESSOR_MODEL", name = "Modelo del procesador", valueName = "Apple A16 Bionic"),
            Attribute(id = "WEIGHT", name = "Peso", valueName = "171 g")
        )
    )

    val results = listOf(
        item1,
        item2,
        item3,
        item4,
        item5,
        item6
    )

    val dummyState = SearchState(
        isLoading = false,
        error = null,
        results = results
    )

    MeliAppTheme {
        ResultScreen(
            padding = PaddingValues(top = 24.dp),
            state = dummyState,
            onIntent = {}
        )
    }
}
