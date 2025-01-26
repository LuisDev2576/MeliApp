package com.luis.dev.meliapp.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.luis.dev.meliapp.features.details.presentation.DetailsScreen
import com.luis.dev.meliapp.features.home.presentation.HomeScreen
import com.luis.dev.meliapp.features.details.presentation.DetailsViewModel
import com.luis.dev.meliapp.features.results.presentation.ResultsScreen
import com.luis.dev.meliapp.features.results.presentation.ResultsViewModel
import com.luis.dev.meliapp.core.components.searchTopAppBar.SearchBarViewModel
import com.luis.dev.meliapp.core.components.searchTopAppBar.SearchTopAppBar
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            val searchBarViewModel: SearchBarViewModel = koinViewModel()
            val searchBarState = searchBarViewModel.state.collectAsState()
            SearchTopAppBar(
                state = searchBarState.value,
                onIntent = { intent -> searchBarViewModel.handleIntent(intent) },
                modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars),
                navToResultScreen = { productName ->
                    navController.navigate(Route.Results(productName))
                }
            )
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Route.Home,
            modifier = Modifier
                .padding(padding)
        ) {
            composable<Route.Home> {
                HomeScreen()
                BackHandler {

                }
            }
            composable<Route.Results> {
                val resultsViewModel: ResultsViewModel = koinViewModel()
                val resultsState = resultsViewModel.state.collectAsState().value
                ResultsScreen(
                    resultsState = resultsState,
                    navToDetailScreen = { productId ->
                        navController.navigate(Route.Details(productId))
                    },
                    onBack = {
                        navController.navigate(Route.Home)
                    }
                )
            }
            composable<Route.Details> {
                val detailsViewModel: DetailsViewModel = koinViewModel()
                val detailsState = detailsViewModel.state.collectAsState().value
                DetailsScreen(
                    detailsState = detailsState,
                )
            }
        }
    }
}
