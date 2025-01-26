package com.luis.dev.meliapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.luis.dev.meliapp.navigation.AppNavigation
import com.luis.dev.meliapp.ui.theme.MeliAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MeliAppTheme {
                AppNavigation()
            }
        }
    }
}
