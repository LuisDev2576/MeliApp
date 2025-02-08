package com.luis.dev.meliapp.features.authentication.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.luis.dev.meliapp.features.authentication.data.datasource.AuthenticationDataSource
import com.luis.dev.meliapp.features.authentication.data.datasource.AuthenticationDataSourceImpl
import com.luis.dev.meliapp.features.authentication.data.repository.AuthRepository
import com.luis.dev.meliapp.features.authentication.data.repository.AuthRepositoryImpl
import com.luis.dev.meliapp.features.authentication.domain.usecases.LoginUseCase
import com.luis.dev.meliapp.features.authentication.domain.usecases.RegisterUseCase
import com.luis.dev.meliapp.features.authentication.domain.usecases.ResetPasswordUseCase
import com.luis.dev.meliapp.features.authentication.presentation.login.LoginViewModel
import com.luis.dev.meliapp.features.authentication.presentation.register.RegisterViewModel
import com.luis.dev.meliapp.features.authentication.presentation.reset.ResetPasswordViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Módulo de Koin para la configuración de las dependencias relacionadas con la autenticación.
 * Proporciona instancias de Firebase, DataSource, Repositorios, Casos de Uso y ViewModels.
 */
val AuthenticationModule = module {

    // Instancias de Firebase
    /**
     * Proporciona una instancia singleton de [FirebaseAuth].
     */
    single { FirebaseAuth.getInstance() }

    /**
     * Proporciona una instancia singleton de [FirebaseFirestore].
     */
    single { FirebaseFirestore.getInstance() }

    // DataSource
    /**
     * Proporciona una implementación de [AuthenticationDataSource].
     * Utiliza [FirebaseAuth] y el contexto de la aplicación para manejar la lógica de autenticación.
     */
    single<AuthenticationDataSource> {
        AuthenticationDataSourceImpl(
            context = get(),
            firebaseAuth = get()
        )
    }

    // Repositorio principal de Auth
    /**
     * Proporciona una implementación de [AuthRepository] que utiliza [AuthenticationDataSource].
     */
    single<AuthRepository> { AuthRepositoryImpl(dataSource = get()) }

    // UseCases
    /**
     * Caso de uso para iniciar sesión.
     * Depende del repositorio de autenticación.
     */
    factory { LoginUseCase(repository = get()) }

    /**
     * Caso de uso para registrar un nuevo usuario.
     * Depende del repositorio de autenticación.
     */
    factory { RegisterUseCase(repository = get()) }

    /**
     * Caso de uso para restablecer la contraseña.
     * Depende del repositorio de autenticación.
     */
    factory { ResetPasswordUseCase(repository = get()) }

    // ViewModels
    /**
     * ViewModel para manejar la lógica de la pantalla de inicio de sesión.
     * Depende del caso de uso [LoginUseCase].
     */
    viewModel {
        LoginViewModel(loginUseCase = get())
    }

    /**
     * ViewModel para manejar la lógica de la pantalla de registro.
     * Depende del caso de uso [RegisterUseCase].
     */
    viewModel {
        RegisterViewModel(registerUseCase = get())
    }

    /**
     * ViewModel para manejar la lógica de la pantalla de restablecimiento de contraseña.
     * Depende del caso de uso [ResetPasswordUseCase].
     */
    viewModel {
        ResetPasswordViewModel(resetPasswordUseCase = get())
    }
}