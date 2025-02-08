package com.luis.dev.meliapp.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.luis.dev.meliapp.core.components.searchTopAppBar.SearchBarViewModel
import com.luis.dev.meliapp.core.components.searchTopAppBar.SearchTopBar
import com.luis.dev.meliapp.features.authentication.presentation.login.LoginScreen
import com.luis.dev.meliapp.features.authentication.presentation.login.LoginViewModel
import com.luis.dev.meliapp.features.authentication.presentation.register.RegisterScreen
import com.luis.dev.meliapp.features.authentication.presentation.register.RegisterViewModel
import com.luis.dev.meliapp.features.authentication.presentation.reset.ResetPasswordScreen
import com.luis.dev.meliapp.features.authentication.presentation.reset.ResetPasswordViewModel
import com.luis.dev.meliapp.features.details.presentation.DetailsScreen
import com.luis.dev.meliapp.features.details.presentation.DetailsViewModel
import com.luis.dev.meliapp.features.home.presentation.HomeScreen
import com.luis.dev.meliapp.features.results.presentation.ResultsScreen
import com.luis.dev.meliapp.features.results.presentation.ResultsViewModel
import kotlinx.serialization.ExperimentalSerializationApi
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSerializationApi::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination: NavDestination? = navBackStackEntry.value?.destination
    val currentRoute = currentDestination?.route

    val topBarExcludedRoutes = listOf(
        Route.Login.serializer().descriptor.serialName,
        Route.Register.serializer().descriptor.serialName,
        Route.RecoverPassword.serializer().descriptor.serialName
    )

    Scaffold(
        topBar = {
            if (currentRoute !in topBarExcludedRoutes) {
                val searchBarViewModel: SearchBarViewModel = koinViewModel()
                val searchBarState = searchBarViewModel.state.collectAsState()
                SearchTopBar(
                    state = searchBarState.value,
                    onIntent = { intent -> searchBarViewModel.handleIntent(intent) },
                    modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars),
                    onNavigateToResultScreen = { productName ->
                        navController.navigate(Route.Results(productName))
                    }
                )
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Route.Login,
            modifier = Modifier
                .padding(padding)
        ) {
            composable<Route.Login> {
                val loginViewModel: LoginViewModel = koinViewModel()
                val loginState = loginViewModel.state.collectAsState().value

                LoginScreen(
                    loginState = loginState,
                    onIntent = { loginViewModel.handleIntent(it) },
                    onNavigateToRegister = { navController.navigate(Route.Register) },
                    onNavigateToReset = { navController.navigate(Route.RecoverPassword) },
                    onNavigateBack = { },
                    onLoginSuccess = { navController.navigate(Route.Home) }
                )
            }

            composable<Route.Register> {
                val registerViewModel: RegisterViewModel = koinViewModel()
                val registerState = registerViewModel.state.collectAsState().value

                RegisterScreen(
                    state = registerState,
                    onIntent = { registerViewModel.handleIntent(it) },
                    onNavigateToLogin = { navController.navigate(Route.Login) },
                    onRegistrationSuccess = { navController.navigate(Route.Home) }
                )
            }

            composable<Route.RecoverPassword> {
                val resetViewModel: ResetPasswordViewModel = koinViewModel()
                val resetState = resetViewModel.state.collectAsState().value

                ResetPasswordScreen(
                    state = resetState,
                    onIntent = { resetViewModel.handleIntent(it) },
                    onNavigateBack = { navController.navigate(Route.Login) }
                )
            }

            composable<Route.Home> {
                HomeScreen(
                    onBack = {
                    }
                )
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
                    detailsState = detailsState
                )
            }
        }
    }
}