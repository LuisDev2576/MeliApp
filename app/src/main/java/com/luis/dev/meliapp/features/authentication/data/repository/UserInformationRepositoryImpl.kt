package com.luis.dev.meliapp.features.authentication.data.repository

import android.content.Context
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.luis.dev.meliapp.features.authentication.data.model.UserInformation
import kotlinx.coroutines.tasks.await

class UserInformationRepositoryImpl(
    private val context: Context,
    private val firestore: FirebaseFirestore,
) : UserInformationRepository {

    override suspend fun downloadUserInformation(uid: String): UserInformation? {
        return try {
            val doc = firestore.collection("users").document(uid).get().await()
            doc.toObject(UserInformation::class.java) ?: run {
                Log.e("UserInformation", "No se encontró la info del usuario con UID: $uid")
                null
            }
        } catch (e: Exception) {
            Log.e("UserInformation", "Error descargando info de usuario", e)
            null
        }
    }

    override suspend fun uploadUserInformation(uid: String, userInformation: UserInformation): Boolean {
        return try {
            firestore.collection("users").document(uid).set(userInformation.copy(uid = uid)).await()
            true
        } catch (e: Exception) {
            Log.e("UserInformation", "Error subiendo info de usuario", e)
            false
        }
    }
}
