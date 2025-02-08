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
 * Módulo de Koin para la configuración de dependencias relacionadas con los resultados de búsqueda.
 * Proporciona instancias del DataSource, Repositorio, Caso de Uso y ViewModel.
 */
val ResultsModule = module {

    /**
     * Proporciona una implementación de [ResultsDataSource] para interactuar con la API y obtener resultados de búsqueda.
     * Utiliza un cliente HTTP proporcionado por la dependencia de Koin.
     */
    single<ResultsDataSource> {
        ResultsDataSourceImpl(
            httpClient = get()
        )
    }

    /**
     * Proporciona una implementación de [ResultsRepository] para manejar la lógica de negocio relacionada
     * con los resultados de búsqueda. Incluye un sitio predeterminado ("MLA" para Argentina).
     */
    single<ResultsRepository> {
        ResultsRepositoryImpl(
            dataSource = get(),
            defaultSiteId = "MLA"
        )
    }

    /**
     * Proporciona una implementación de [GetResultsUseCase] para encapsular la lógica
     * de obtención de resultados de búsqueda desde el repositorio.
     */
    single<GetResultsUseCase> {
        GetResultsUseCaseImpl(repository = get())
    }

    /**
     * Proporciona una instancia de [ResultsViewModel] para manejar el estado y la lógica de la pantalla de resultados de búsqueda.
     * Depende del caso de uso [GetResultsUseCase] y del [SavedStateHandle].
     */
    viewModel {
        ResultsViewModel(
            getResultsUseCase = get(),
            savedStateHandle = get()
        )
    }
}