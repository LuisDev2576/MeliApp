package com.luis.dev.meliapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorScheme = darkColorScheme(
    primary = Canary,
    onPrimary = DelftBlue,
    secondary = Gold,
    onSecondary = DelftBlue,
    tertiary = DelftBlue,
    onTertiary = White,
    background = White,
    onBackground = Black
)

private val LightColorScheme = lightColorScheme(
    primary = Canary,
    onPrimary = DelftBlue,
    secondary = Gold,
    onSecondary = DelftBlue,
    tertiary = DelftBlue,
    onTertiary = White,
    background = White,
    onBackground = Black
)

@Composable
fun MeliAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !darkTheme

    SideEffect {
        systemUiController.setStatusBarColor(
            color = colorScheme.primary,
            darkIcons = useDarkIcons
        )
        systemUiController.setNavigationBarColor(
            color = colorScheme.onBackground,
            darkIcons = !useDarkIcons
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}