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
 * Módulo de Koin para la configuración de dependencias relacionadas con la funcionalidad de detalles.
 * Proporciona instancias del DataSource, Repositorio, Caso de Uso y ViewModel.
 */
val detailsModule = module {

    /**
     * Proporciona una implementación de [DetailsDataSource] para manejar la obtención de datos de detalles de un ítem.
     * Utiliza un cliente HTTP para realizar las solicitudes.
     */
    single<DetailsDataSource> {
        DetailsDataSourceImpl(
            httpClient = get()
        )
    }

    /**
     * Proporciona una implementación de [DetailsRepository] para manejar la lógica de negocio
     * relacionada con la obtención de detalles de un ítem.
     * Depende de [DetailsDataSource].
     */
    single<DetailsRepository> {
        DetailsRepositoryImpl(
            dataSource = get()
        )
    }

    /**
     * Proporciona una implementación de [GetItemDetailUseCase] para ejecutar la lógica de obtención
     * de detalles de un ítem desde el repositorio.
     */
    single<GetItemDetailUseCase> {
        GetItemDetailUseCaseImpl(
            repository = get()
        )
    }

    /**
     * Proporciona una instancia de [DetailsViewModel] para manejar el estado y la lógica
     * de la pantalla de detalles.
     * Depende del caso de uso [GetItemDetailUseCase] y del [SavedStateHandle].
     */
    viewModel {
        DetailsViewModel(
            getItemDetailUseCase = get(),
            savedStateHandle = get()
        )
    }
}
