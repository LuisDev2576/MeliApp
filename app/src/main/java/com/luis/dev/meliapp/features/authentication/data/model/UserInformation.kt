// features/authentication/domain/model/UserInformation.kt

package com.luis.dev.meliapp.features.authentication.data.model

data class UserInformation(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    // Por ejemplo, historial de búsquedas
    val searchHistory: List<String> = emptyList()
)
