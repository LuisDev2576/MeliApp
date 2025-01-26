package com.luis.dev.meliapp.features.details.di

import com.luis.dev.meliapp.features.details.data.datasource.DetailsDataSource
import com.luis.dev.meliapp.features.details.data.datasource.DetailsDataSourceImpl
import com.luis.dev.meliapp.features.details.data.repository.DetailsRepository
import com.luis.dev.meliapp.features.details.data.repository.DetailsRepositoryImpl
import com.luis.dev.meliapp.features.details.domain.usecases.GetItemDetailUseCase
import com.luis.dev.meliapp.features.details.domain.usecases.GetItemDetailUseCaseImpl
import com.luis.dev.meliapp.features.details.presentation.DetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Módulo de Koin para la feature de detailsModule.
 */
val detailsModule = module {

    // DataSource
    single<DetailsDataSource> {
        DetailsDataSourceImpl(
            httpClient = get()
        )
    }

    // Repository
    single<DetailsRepository> {
        DetailsRepositoryImpl(
            dataSource = get()
        )
    }

    // UseCase
    single<GetItemDetailUseCase> {
        GetItemDetailUseCaseImpl(
            repository = get()
        )
    }

    // ViewModel
    viewModel {
        DetailsViewModel(
            getItemDetailUseCase = get(),
            savedStateHandle = get()
        )
    }
}
