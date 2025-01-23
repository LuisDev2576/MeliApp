package com.luis.dev.meliapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.luis.dev.meliapp.features.home.presentation.HomeScreen
import com.luis.dev.meliapp.features.result_list.presentation.ResultScreen
import com.luis.dev.meliapp.features.search.presentation.SearchViewModel
import com.luis.dev.meliapp.ui.theme.MeliAppTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MeliAppTheme {
                Scaffold { padding ->
                    val searchViewModel: SearchViewModel = koinViewModel()
                    val uiState = searchViewModel.state.collectAsState()
                    ResultScreen(
                        padding = padding,
                        state = uiState.value,
                        onIntent = { intent -> searchViewModel.handleIntent(intent) }
                    )
                }
            }
        }
    }
}
