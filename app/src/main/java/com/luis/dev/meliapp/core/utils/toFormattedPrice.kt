package com.luis.dev.meliapp.core.utils

fun Int.toFormattedPrice(): String {
    return this.toString().reversed().chunked(3).joinToString(".").reversed()
}