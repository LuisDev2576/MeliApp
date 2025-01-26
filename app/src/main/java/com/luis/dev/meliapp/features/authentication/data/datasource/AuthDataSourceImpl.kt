package com.luis.dev.meliapp.features.authentication.data.datasource

import android.content.Context
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseUser
import com.luis.dev.meliapp.R
import com.luis.dev.meliapp.features.authentication.data.repository.AuthRepositoryResult
import com.luis.dev.meliapp.features.authentication.data.repository.RegistrationRepositoryResult
import com.luis.dev.meliapp.features.authentication.data.repository.ResetPasswordRepositoryResult
import kotlinx.coroutines.tasks.await

class AuthDataSourceImpl(
    private val context: Context,
    private val firebaseAuth: FirebaseAuth,
) : AuthDataSource {

    override suspend fun loginUser(email: String, password: String): AuthRepositoryResult<FirebaseUser> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val user = result.user
            if (user != null) {
                AuthRepositoryResult.Success(user)
            } else {
                AuthRepositoryResult.Error("Usuario no encontrado.")
            }
        } catch (e: FirebaseAuthException) {
            when (e.errorCode) {
                "ERROR_INVALID_EMAIL" -> AuthRepositoryResult.Error(context.getString(R.string.error_invalid_email))
                "ERROR_USER_NOT_FOUND" -> AuthRepositoryResult.Error(context.getString(R.string.error_user_not_found))
                "ERROR_WRONG_PASSWORD" -> AuthRepositoryResult.Error(context.getString(R.string.error_wrong_password))
                else -> AuthRepositoryResult.Error(e.message ?: "Error desconocido.")
            }
        } catch (e: FirebaseTooManyRequestsException) {
            AuthRepositoryResult.Error(context.getString(R.string.error_too_many_requests))
        } catch (e: Exception) {
            AuthRepositoryResult.Error(e.message ?: "Error al iniciar sesión.")
        }
    }

    override suspend fun registerUser(email: String, password: String): RegistrationRepositoryResult<FirebaseUser> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user
            if (user != null) {
                RegistrationRepositoryResult.Success(user)
            } else {
                RegistrationRepositoryResult.Error("No se pudo registrar el usuario.")
            }
        } catch (e: FirebaseAuthException) {
            when (e.errorCode) {
                "ERROR_EMAIL_ALREADY_IN_USE" -> RegistrationRepositoryResult.Error(context.getString(R.string.error_email_already_in_use))
                "ERROR_WEAK_PASSWORD" -> RegistrationRepositoryResult.Error(context.getString(R.string.error_weak_password))
                "ERROR_INVALID_EMAIL" -> RegistrationRepositoryResult.Error(context.getString(R.string.error_invalid_email))
                else -> RegistrationRepositoryResult.Error(e.message ?: "Error desconocido en registro.")
            }
        } catch (e: Exception) {
            RegistrationRepositoryResult.Error(e.message ?: "Error general al registrar.")
        }
    }

    override suspend fun resetPassword(email: String): ResetPasswordRepositoryResult<Unit> {
        return try {
            firebaseAuth.sendPasswordResetEmail(email).await()
            ResetPasswordRepositoryResult.Success(Unit)
        } catch (e: FirebaseAuthInvalidUserException) {
            ResetPasswordRepositoryResult.Error(context.getString(R.string.error_user_not_found))
        } catch (e: FirebaseTooManyRequestsException) {
            ResetPasswordRepositoryResult.Error(context.getString(R.string.error_too_many_requests))
        } catch (e: Exception) {
            ResetPasswordRepositoryResult.Error(e.message ?: "Error desconocido en reset password.")
        }
    }

    override fun currentUser(): FirebaseUser? = firebaseAuth.currentUser

    override fun signOut() {
        firebaseAuth.signOut()
    }
}
