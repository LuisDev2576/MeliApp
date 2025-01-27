package com.luis.dev.meliapp.core.utils

/**
 * Extensión para formatear un entero como un precio con separadores de miles.
 *
 * @receiver Entero que se desea formatear.
 * @return Una cadena que representa el número formateado con separadores de miles (ejemplo: 1000000 → "1.000.000").
 */
fun Int.toFormattedPrice(): String {
    return this.toString()
        .reversed()
        .chunked(3)
        .joinToString(".")
        .reversed()
}