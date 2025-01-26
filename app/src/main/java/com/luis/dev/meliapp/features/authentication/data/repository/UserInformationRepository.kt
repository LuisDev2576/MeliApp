package com.luis.dev.meliapp.features.authentication.data.repository

import com.luis.dev.meliapp.features.authentication.data.model.UserInformation

interface UserInformationRepository {
    suspend fun downloadUserInformation(uid: String): UserInformation?
    suspend fun uploadUserInformation(uid: String, userInformation: UserInformation): Boolean
}
