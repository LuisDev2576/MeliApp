package com.luis.dev.meliapp.core.utils

/**
 * Extensión para convertir una URL en formato HTTP a HTTPS.
 *
 * @receiver Cadena que representa una URL.
 * @return La misma URL pero con el esquema "https://" si originalmente empezaba con "http://".
 * Si la URL ya utiliza "https://" o no comienza con "http://", se devuelve sin cambios.
 */
fun String.ConvertToHttps(): String {
    return if (this.startsWith("http://")) {
        this.replaceFirst("http://", "https://")
    } else {
        this
    }
}