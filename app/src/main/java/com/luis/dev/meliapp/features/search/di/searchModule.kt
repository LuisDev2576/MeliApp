package com.luis.dev.meliapp.features.search.di

import com.luis.dev.meliapp.features.search.data.datasource.SearchDataSource
import com.luis.dev.meliapp.features.search.data.datasource.SearchDataSourceImpl
import com.luis.dev.meliapp.features.search.data.repository.SearchRepository
import com.luis.dev.meliapp.features.search.data.repository.SearchRepositoryImpl
import com.luis.dev.meliapp.features.search.domain.usecases.SearchUseCase
import com.luis.dev.meliapp.features.search.domain.usecases.SearchUseCaseImpl
import com.luis.dev.meliapp.features.search.presentation.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Módulo de Koin específico para la feature de búsqueda (search).
 */
val searchModule = module {

    // DataSource para la búsqueda
    single<SearchDataSource> {
        SearchDataSourceImpl(
            httpClient = get(),    // Se obtiene de globalModule
            jsonFormater = get()   // Se obtiene de globalModule
        )
    }

    // Repository
    single<SearchRepository> {
        SearchRepositoryImpl(
            dataSource = get(),
            defaultSiteId = "MLA"  // O el site que desees
        )
    }

    // UseCase
    single<SearchUseCase> {
        SearchUseCaseImpl(repository = get())
    }

    // ViewModel
    viewModel {
        SearchViewModel(searchUseCase = get())
    }
}
