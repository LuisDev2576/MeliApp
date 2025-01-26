package com.luis.dev.meliapp.navigation

import kotlinx.serialization.Serializable

sealed class Route {

    @Serializable
    object Login : Route()

    @Serializable
    object Register : Route()

    @Serializable
    object RecoverPassword : Route()

    @Serializable
    object Home : Route()

    @Serializable
    data class Details(val productId: String) : Route()

    @Serializable
    data class Results(val productName: String) : Route()

}
