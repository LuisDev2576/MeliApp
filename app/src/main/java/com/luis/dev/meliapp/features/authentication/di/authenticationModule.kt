package com.luis.dev.meliapp.features.authentication.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.luis.dev.meliapp.features.authentication.data.datasource.AuthDataSource
import com.luis.dev.meliapp.features.authentication.data.datasource.AuthDataSourceImpl
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

val authenticationModule = module {

    // Instancias de Firebase
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }

    // DataSource
    single<AuthDataSource> {
        AuthDataSourceImpl(
            context = get(),
            firebaseAuth = get()
        )
    }

    // Repositorio principal de Auth
    single<AuthRepository> { AuthRepositoryImpl(dataSource = get()) }

    // UseCases
    factory { LoginUseCase(repository = get()) }
    factory { RegisterUseCase(repository = get()) }
    factory { ResetPasswordUseCase(repository = get()) }

    // ViewModels
    viewModel {
        LoginViewModel(loginUseCase = get())
    }
    viewModel {
        RegisterViewModel(registerUseCase = get())
    }
    viewModel {
        ResetPasswordViewModel(resetPasswordUseCase = get())
    }
}
