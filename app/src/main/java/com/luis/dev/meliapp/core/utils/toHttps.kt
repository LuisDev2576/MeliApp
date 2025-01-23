package com.luis.dev.meliapp.core.utils

fun String.toHttps(): String {
    return if (this.startsWith("http://")) {
        this.replaceFirst("http://", "https://")
    } else {
        this
    }
}
