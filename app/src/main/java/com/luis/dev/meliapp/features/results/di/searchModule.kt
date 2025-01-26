package com.luis.dev.meliapp.features.results.di

import com.luis.dev.meliapp.features.results.data.datasource.ResultsDataSource
import com.luis.dev.meliapp.features.results.data.datasource.ResultsDataSourceImpl
import com.luis.dev.meliapp.features.results.data.repository.ResultsRepository
import com.luis.dev.meliapp.features.results.data.repository.ResultsRepositoryImpl
import com.luis.dev.meliapp.features.results.domain.usecases.GetResultsUseCase
import com.luis.dev.meliapp.features.results.domain.usecases.GetResultsUseCaseImpl
import com.luis.dev.meliapp.features.results.presentation.ResultsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Módulo de Koin específico para la feature de búsqueda (search).
 */
val resultsModule = module {

    // DataSource para la búsqueda
    single<ResultsDataSource> {
        ResultsDataSourceImpl(
            httpClient = get()
        )
    }

    // Repository
    single<ResultsRepository> {
        ResultsRepositoryImpl(
            dataSource = get(),
            defaultSiteId = "MLA"
        )
    }

    // UseCase
    single<GetResultsUseCase> {
        GetResultsUseCaseImpl(repository = get())
    }

    // ViewModel
    viewModel {
        ResultsViewModel(
            getResultsUseCase = get(),
            savedStateHandle = get()
        )
    }
}
